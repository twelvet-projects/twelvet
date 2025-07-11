package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.*;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.chat.MessageFormat;
import com.alibaba.cloud.ai.dashscope.common.DashScopeApiConstants;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import com.alibaba.cloud.ai.document.DocumentWithScore;
import com.alibaba.cloud.ai.model.RerankModel;
import com.alibaba.cloud.ai.model.RerankRequest;
import com.alibaba.cloud.ai.model.RerankResponse;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.yitter.idgen.YitIdHelper;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.AiKnowledge;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.api.ai.domain.dto.*;
import com.twelvet.api.ai.domain.ocr.InvoiceOCR;
import com.twelvet.api.ai.domain.vo.AiChatHistoryPageVO;
import com.twelvet.api.ai.domain.vo.AiChatHistoryVO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.server.ai.client.AIClient;
import com.twelvet.server.ai.constant.AIDataSourceConstants;
import com.twelvet.server.ai.constant.RAGConstants;
import com.twelvet.server.ai.mapper.*;
import com.twelvet.server.ai.model.MCPService;
import com.twelvet.server.ai.service.AIChatService;
import com.twelvet.server.ai.service.IAiChatHistoryService;
import com.twelvet.server.ai.utils.ModelUtils;
import com.twelvet.server.ai.utils.VectorStoreUtils;
import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * AI助手服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-10-26
 */
@Service
public class AIChatServiceImpl implements AIChatService {

	private final static Logger log = LoggerFactory.getLogger(AIChatServiceImpl.class);

	@Autowired
	private MCPService mcpService;

	/**
	 * 模型配置mapper
	 */
	@Autowired
	private AiModelMapper aiModelMapper;

	/**
	 * 知识库mapper
	 */
	@Autowired
	private AiKnowledgeMapper aiKnowledgeMapper;

	/**
	 * 知识库切片mapper
	 */
	@Autowired
	private AiDocSliceMapper aiDocSliceMapper;

	/**
	 * 聊天历史服务
	 */
	@Autowired
	private IAiChatHistoryService aiChatHistoryService;

	@Autowired
	private AiChatHistoryMapper aiChatHistoryMapper;

	/**
	 * MCP服务mapper
	 */
	@Autowired
	private AiMcpMapper aiMcpMapper;

	/**
	 * 注解是AI请求客户端
	 */
	@Autowired
	private AIClient aiClient;

	/**
	 * 敏感词检查
	 */
	@Autowired
	private SensitiveWordBs sensitiveWordBs;

	/**
	 * 文字model
	 */
	@Autowired(required = false)
	private DashScopeChatModel dashScopeChatModel;

	/**
	 * 图像model
	 */
	@Autowired(required = false)
	private DashScopeImageModel dashScopeImageModel;

	/**
	 * 语音转文字model
	 */
	@Autowired(required = false)
	private DashScopeAudioTranscriptionModel dashScopeAudioTranscriptionModel;

	/**
	 * 多模态测试图片地址
	 */
	private final static String MULTI_IMAGE_FILE_URL = "https://static.twelvet.cn/ai/dog_and_girl.jpeg";

	/**
	 * OCR识别
	 */
	private final static String OCR_IMAGE_FILE_URL = "https://static.twelvet.cn/ai/ocr.jpg";

	/**
	 * 多模态测试视频地址
	 */
	private final static String MULTI_VIDEO_FILE_URL = "https://static.twelvet.cn/ai/video.mp4";

	/**
	 * 发起聊天
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public Flux<MessageVO> chatStream(MessageDTO messageDTO) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		String userId = String.valueOf(loginUser.getUserId());

		// 检查是否存在敏感词
		if (sensitiveWordBs.contains(messageDTO.getContent())) {
			log.error("用户输入内容检查出敏感词");
			MessageVO messageVO = new MessageVO();
			messageVO.setContent("用户内容存在敏感词");
			return Flux.just(messageVO);
		}

		AiKnowledge aiKnowledge = aiKnowledgeMapper.selectAiKnowledgeByKnowledgeId(messageDTO.getKnowledgeId());
		if (Objects.isNull(aiKnowledge)) {
			MessageVO messageVO = new MessageVO();
			messageVO.setContent("此知识库不存在");
			return Flux.just(messageVO);
		}

		// 历史消息搜索，并且插入用户提问消息
		CompletableFuture<List<Message>> messagesCompletableFuture = CompletableFuture.supplyAsync(() -> {
			try {
				// CompletableFuture，手动切换数据源
				DynamicDataSourceContextHolder.push(AIDataSourceConstants.DS_MASTER);
				// 加入历史对话
				List<Message> messages = new ArrayList<>();
				// 执行历史消息搜索
				if (Boolean.TRUE.equals(messageDTO.getCarryContextFlag())) {
					SearchAiChatHistoryDTO searchAiChatHistoryDTO = new SearchAiChatHistoryDTO();
					searchAiChatHistoryDTO.setUserId(userId);
					searchAiChatHistoryDTO.setMultiRound(aiKnowledge.getMultiRound());
					searchAiChatHistoryDTO.setKnowledgeId(aiKnowledge.getKnowledgeId());
					List<AiChatHistoryVO> aiChatHistoryList = aiChatHistoryService
						.selectAiChatHistoryListByUserId(searchAiChatHistoryDTO);
					// 反转对话
					Collections.reverse(aiChatHistoryList);
					for (AiChatHistoryVO aiChatHistoryVO : aiChatHistoryList) {
						RAGEnums.UserTypeEnums createByType = aiChatHistoryVO.getCreateByType();
						String content = aiChatHistoryVO.getContent();
						if (RAGEnums.UserTypeEnums.USER.equals(createByType)) {
							messages.add(new UserMessage(content));
						}
						else if (RAGEnums.UserTypeEnums.AI.equals(createByType)) {
							messages.add(new AssistantMessage(content));
						}
						else {
							throw new TWTException("无法匹配对应的会话用户类型");
						}
					}
				}

				// 搜索完成插入用户提问消息
				LocalDateTime userNow = LocalDateTime.now();
				// 储存用户提问
				AiChatHistoryDTO userAIChatHistoryDTO = new AiChatHistoryDTO();
				// 生成唯一消息雪花ID
				String userMsgId = String.valueOf(YitIdHelper.nextId());
				userAIChatHistoryDTO.setMsgId(userMsgId);
				userAIChatHistoryDTO.setUserId(userId);
				userAIChatHistoryDTO.setKnowledgeId(aiKnowledge.getKnowledgeId());
				userAIChatHistoryDTO.setSendUserId(userId);
				userAIChatHistoryDTO.setSendUserName(loginUser.getUsername());
				// 设置消息归属人
				userAIChatHistoryDTO.setCreateByType(RAGEnums.UserTypeEnums.USER);
				userAIChatHistoryDTO.setContent(messageDTO.getContent());
				userAIChatHistoryDTO.setCreateTime(userNow);
				aiChatHistoryService.insertAiChatHistory(userAIChatHistoryDTO);

				return messages;
			}
			finally {
				// 清理使用
				DynamicDataSourceContextHolder.poll();
			}
		}, TUtils.threadPoolExecutor);

		// 向量库搜索
		CompletableFuture<List<Document>> vectorCompletableFuture = CompletableFuture.supplyAsync(() -> {
			// 指定过滤元数据
			FilterExpressionBuilder filterExpressionBuilder = new FilterExpressionBuilder();
			// 从向量数据库中搜索相似文档
			Filter.Expression filter = filterExpressionBuilder
				.eq(RAGEnums.VectorMetadataEnums.KNOWLEDGE_ID.getCode(), aiKnowledge.getKnowledgeId())
				.build();
			SearchRequest searchRequest = SearchRequest.builder()
				// 搜索向量内容
				.query(messageDTO.getContent())
				// 向量匹配最多条数
				.topK(aiKnowledge.getTopK())
				// 匹配相似度准度
				.similarityThreshold(aiKnowledge.getScore())
				// 过滤元数据
				.filterExpression(filter)
				.build();

			VectorStore vectorStore = VectorStoreUtils.getVectorStore();
			return vectorStore.similaritySearch(searchRequest);

		}, TUtils.threadPoolExecutor);

		// 获取使用的模型信息
		CompletableFuture<AiModel> aiModelCompletableFuture = CompletableFuture.supplyAsync(() -> {
			return aiModelMapper.selectAiModelByModelDefault(ModelEnums.ModelTypeEnums.LLM);
		}, TUtils.threadPoolExecutor);

		// 获取使用的排序模型信息
		CompletableFuture<AiModel> aiRerankModelCompletableFuture = CompletableFuture.supplyAsync(() -> {
			return aiModelMapper.selectAiModelByModelDefault(ModelEnums.ModelTypeEnums.RERANKER);
		}, TUtils.threadPoolExecutor);

		CompletableFuture
			.allOf(vectorCompletableFuture, messagesCompletableFuture, aiModelCompletableFuture,
					aiRerankModelCompletableFuture)
			.join();

		// 获取模型信息
		AiModel aiModel = aiModelCompletableFuture.join();
		AiModel rerankModel = aiRerankModelCompletableFuture.join();

		// 向量知识库
		List<Document> vectorDocs = vectorCompletableFuture.join();
		// 召回结果 重排
		if (CollUtil.isNotEmpty(vectorDocs)) {
			RerankModel dashScopeRerankModel = ModelUtils.modelServiceProviderSelector(rerankModel.getModelProvider())
				.getRerankModel(rerankModel);

			RerankRequest rerankRequest = new RerankRequest(messageDTO.getContent(), vectorDocs);
			RerankResponse rerankResponse = dashScopeRerankModel.call(rerankRequest);
			List<DocumentWithScore> results = rerankResponse.getResults();
			vectorDocs = results.stream().map(DocumentWithScore::getOutput).collect(Collectors.toList());
		}

		// 历史对话
		List<Message> messages = messagesCompletableFuture.join();

		if (CollectionUtil.isNotEmpty(vectorDocs)) {
			// 获取切片ID
			List<Long> sliceIdList = new ArrayList<>();
			for (Document document : vectorDocs) {
				Map<String, Object> metadata = document.getMetadata();
				// 储存命中次数
				/*
				 * Long sliceId = (Long)
				 * metadata.get(RAGEnums.VectorMetadataEnums.SLICE_ID.getCode()); if
				 * (Objects.nonNull(sliceId) && !sliceIdList.contains(sliceId)) {
				 * sliceIdList.add(sliceId); }
				 */
			}
			// TODO 进行加1处理切片

			// 加入知识库内容
			// 获取documents里的content
			String documentContext = vectorDocs.stream()
				.map(Document::getText)
				.collect(Collectors.joining(System.lineSeparator()));
			// 创建系统提示词
			SystemPromptTemplate promptTemplate = new SystemPromptTemplate(RAGConstants.RAG_SYSTEM_PROMPT);
			// 填充数据
			Message systemMessage = promptTemplate.createMessage(Map.of("question_answer_context", documentContext));
			messages.add(systemMessage);
		}
		else { // 无法匹配返回找不到相关信息
			if (Boolean.FALSE) { // TODO 需要获取配置是否找不到直接取消对话
				MessageVO messageVO = new MessageVO();
				messageVO.setContent("知识库未匹配相关问题，请重新提问");
				return Flux.just(messageVO);
			}
		}

		// 储存AI回答
		// 回复时间必须保证在用户提问时间之前（重新获取时间，并且增加1毫秒），保证排序
		LocalDateTime replyNow = LocalDateTime.now().plusNanos(1_000_000);
		// 生成唯一消息雪花ID
		String aiMsgId = String.valueOf(YitIdHelper.nextId());
		// ai回复内容
		StringBuffer aiContent = new StringBuffer();

		// 加入用户提问
		if (RAGEnums.ChatTypeEnums.TEXT.equals(messageDTO.getChatType())) { // 纯文本提问
			UserMessage userMessage = new UserMessage(messageDTO.getContent());
			messages.add(userMessage);
		}
		else if (RAGEnums.ChatTypeEnums.IMAGES.equals(messageDTO.getChatType())) { // 图片提问
			try {
				List<Media> mediaList = List
					.of(new Media(MimeTypeUtils.IMAGE_PNG, new URI(MULTI_IMAGE_FILE_URL).toURL().toURI()));

				UserMessage userMessage = UserMessage.builder()
					.text(messageDTO.getContent())
					.media(mediaList)
					.metadata(new HashMap<>())
					.build();
				userMessage.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);

				messages.add(userMessage);
			}
			catch (Exception e) {
				log.error("创建多模态提问失败", e);
				MessageVO messageVO = new MessageVO();
				messageVO.setContent("创建多模态提问失败");
				return Flux.just(messageVO);
			}
		}

		Prompt prompt = new Prompt(messages);

		ChatModel chatModel = ModelUtils.modelServiceProviderSelector(aiModel.getModelProvider()).getChatModel(aiModel);

		return ChatClient
			// 自定义使用不同的大模型
			.create(chatModel)
			.prompt(prompt)
			// 功能选择
			// .options(dashScopeChatOptions)
			// MCP
			.toolCallbacks(mcpService.loadingMCP())
			// 本地工具
			// .tools(new MockOrderService(), new MockWeatherService(), new
			// MockSearchService())
			.stream()
			.chatResponse()
			.map(chatResponse -> {
				MessageVO messageVO = new MessageVO();
				String content = chatResponse.getResult().getOutput().getText();
				messageVO.setMsgId(aiMsgId);
				messageVO.setContent(content);
				// 储存AI回复内容
				aiContent.append(content);
				return messageVO;
			})
			.doFinally(signalType -> {
				if (Arrays.asList(SignalType.CANCEL, SignalType.ON_COMPLETE).contains(signalType)) { // 取消链接时或完成输出时
					// 储存AI提问
					AiChatHistoryDTO aiChatHistoryDTO = new AiChatHistoryDTO();
					aiChatHistoryDTO.setMsgId(aiMsgId);
					aiChatHistoryDTO.setUserId(userId);
					aiChatHistoryDTO.setKnowledgeId(aiKnowledge.getKnowledgeId());
					aiChatHistoryDTO.setSendUserId(userId);
					aiChatHistoryDTO.setSendUserName(loginUser.getUsername());
					// 设置消息归属人
					aiChatHistoryDTO.setCreateByType(RAGEnums.UserTypeEnums.AI);
					aiChatHistoryDTO.setContent(aiContent.toString());
					aiChatHistoryDTO.setCreateTime(replyNow);
					try {
						// CompletableFuture，手动切换数据源
						DynamicDataSourceContextHolder.push(AIDataSourceConstants.DS_MASTER);
						aiChatHistoryService.insertAiChatHistory(aiChatHistoryDTO);
					}
					finally {
						// 清理使用
						DynamicDataSourceContextHolder.poll();
					}

				}
			});
	}

	/**
	 * 根据知识库ID获取聊天记录分页
	 * @param aiChatHistory AiChatHistory
	 * @return AiChatHistoryPageVO
	 */
	@Override
	public List<AiChatHistoryPageVO> chatHistoryPage(AiChatHistory aiChatHistory) {
		Long userId = SecurityUtils.getLoginUser().getUserId();
		aiChatHistory.setUserId(String.valueOf(userId));
		List<AiChatHistoryPageVO> aiChatHistoryPageVOS = aiChatHistoryMapper
			.selectKnowledgeAiChatHistoryListByUserId(aiChatHistory);
		CollectionUtil.reverse(aiChatHistoryPageVOS);
		return aiChatHistoryPageVOS;
	}

	/**
	 * 多模态回答用户问题
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public Flux<MessageVO> multiChatStream(MessageDTO messageDTO) {
		File file = null;
		String TEMP_IMAGE_DIR = "twelvet_ai_temp";
		String filePath = String.format("%s%s", System.getProperty("java.io.tmpdir"), TEMP_IMAGE_DIR);
		try {
			UserMessage userMessage;
			if (Boolean.FALSE) { // 图片
				List<Media> mediaList = List
					.of(new Media(MimeTypeUtils.IMAGE_JPEG, new URI(MULTI_IMAGE_FILE_URL).toURL().toURI()));
				userMessage = UserMessage.builder()
					.text(messageDTO.getContent())
					.media(mediaList)
					.metadata(new HashMap<>())
					.build();
				// 设置文件格式
				userMessage.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);
			}
			else { // 视频
					// TODO 目前非真正的视频识别，官方例子为把视频切成图片进行识别
					// 视频转为图片，取出视频声音进行多次的AI分析总结得到最终的评价
					// 创建File对象，表示目录
					// 开启日志
				FFmpegLogCallback.set();

				File directory = new File(filePath);

				// 判断目录是否存在
				if (!directory.exists()) {
					// 如果目录不存在，则创建目录
					// mkdirs()方法会创建多级目录，即使父目录不存在也会创建
					boolean isCreated = directory.mkdirs();
					if (isCreated) {
						log.info("目录创建成功：{}", filePath);
					}
					else {
						log.info("目录创建失败！");
					}
				}
				else {
					log.info("目录已存在：{}", filePath);
				}

				String mp3Path = String.format("%s%s%s", filePath, File.separator, "output_audio.acc");
				/*
				 * File mp3File = new File(mp3Path); boolean newFile =
				 * mp3File.createNewFile(); if (!newFile) { log.error("创建音频文件失败"); }
				 */

				// 下载文件
				String fileName = "video_temp.mp4";
				file = aiClient.downloadFile(MULTI_VIDEO_FILE_URL, filePath, fileName, progress -> {
					log.info("total bytes: {}", progress.getTotalBytes()); // 文件大小
					log.info("current bytes: {}", progress.getCurrentBytes()); // 已下载字节数
					log.info("progress: {}", Math.round(progress.getRate() * 100) + "%"); // 已下载百分比
					if (progress.isDone()) { // 是否下载完成
						log.info("--------   Download Completed!   --------");
					}
				});

				// ffmpeg进行切图
				List<String> tempList = new ArrayList<>();

				FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file.getPath());
				ff.start();
				ff.setFormat("mp4");
				try (Java2DFrameConverter converter = new Java2DFrameConverter();
						FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mp3Path, ff.getAudioChannels());) {

					// 取出视频中的声音作为语音基于大模型转为文字后总结
					// recorder.setFormat("aac");
					recorder.setSampleRate(ff.getSampleRate());
					recorder.setAudioCodec(ff.getAudioCodec());
					recorder.setAudioQuality(0); // 最高质量
					recorder.start();
					Frame frameMp3 = null;
					/*
					 * while ((frameMp3 = ff.grabSamples()) != null) { if
					 * (frameMp3.samples != null) { recorder.record(frameMp3); } }
					 */

					// 以下开始取视频图片

					int frameCount = 0; // 当前帧计数
					int secondCount = 0; // 当前秒数计数
					int frameRate = (int) ff.getFrameRate(); // 获取视频帧率

					int length = ff.getLengthInFrames();
					log.info("文件大小共: {}", length);
					Frame frame;
					for (int i = 1; i < length; i++) {
						frame = ff.grabFrame();
						// 写入音频
						if (frame.samples != null) {
							recorder.record(frame);
						}
						if (frame.image == null) {
							continue;
						}
						frameCount++;
						if (frameCount % (1 * frameRate) == 0) { // 每一秒取图一张
							BufferedImage image = converter.getBufferedImage(frame);
							String path = filePath + "\\" + i + ".png";
							File picFile = new File(path);
							ImageIO.write(image, "png", picFile);
							tempList.add(path);
							log.info("成功剪辑画面：{}", i);
							secondCount++;
						}

					}
				}
				catch (Exception e) {
					log.error("拆解视频失败：", e);
					throw new TWTException("解析失败");
				}
				finally {
					ff.release();
					ff.close();
				}

				/*
				 * String[] commandArray = messageDTO.getContent().split("\n");
				 * ProcessBuilder pb = new ProcessBuilder(commandArray); Process process =
				 * pb.start();
				 */
				List<Media> mediaList = new ArrayList<>();
				for (String png : tempList) {
					if (mediaList.size() > 10) { // 只要十张图片
						break;
					}
					mediaList.add(new Media(MimeTypeUtils.IMAGE_PNG, new PathResource(png)));
				}

				userMessage = UserMessage.builder()
					.text(messageDTO.getContent())
					.media(mediaList)
					.metadata(new HashMap<>())
					.build();
				// 设置文件格式
				userMessage.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.VIDEO);
			}

			Prompt prompt = new Prompt(userMessage, DashScopeChatOptions.builder()
				// 选择支持图片识别的模型
				.withModel("qwen-vl-max-latest")
				.withMultiModel(Boolean.TRUE)
				.build());

			return ChatClient
				// 自定义使用不同的大模型
				.create(dashScopeChatModel)
				.prompt(prompt)
				.stream()
				.chatResponse()
				.map(response -> {
					MessageVO messageVO = new MessageVO();
					messageVO.setContent(Objects.requireNonNull(response).getResult().getOutput().getText());
					return messageVO;
				});

		}
		catch (Exception e) {
			log.error("创建多模态提问失败", e);
			throw new TWTException("创建多模态提问失败");
		}
		finally {
			// 删除临时文件
			if (Objects.nonNull(file) && file.exists()) {
				boolean delete = file.delete();
				if (!delete) {
					log.error("临时文件删除失败");
				}
				else {
					// 创建File对象，表示目录，清理所有临时文件
					File directory = new File(filePath);
					if (directory.exists() && directory.isDirectory()) {
						// 获取目录下的所有文件和子目录
						File[] files = directory.listFiles();

						// 遍历文件数组
						if (files != null) {
							for (File fileTemp : files) {
								// 如果是文件，则删除
								if (fileTemp.isFile()) {
									if (fileTemp.delete()) {
										log.info("Deleted file: {}", fileTemp.getName());
									}
									else {
										log.info("Failed to delete file: {}", fileTemp.getName());
									}
								}
							}
						}
						// 删除当前目录
						if (directory.delete()) {
							log.info("Deleted directory: {}", directory.getName());
						}
						else {
							log.info("Failed to delete directory: {}", directory.getName());
						}
					}
					else {
						log.error("The specified directory does not exist.");
					}
				}
			}
		}
	}

	/**
	 * OCR格式化识别
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public Flux<MessageVO> ocrChatStream(MessageDTO messageDTO) {
		try {

			ParameterizedTypeReference<InvoiceOCR> parameterizedTypeReference = new ParameterizedTypeReference<>() {
			};
			BeanOutputConverter<InvoiceOCR> converter = new BeanOutputConverter<>(parameterizedTypeReference);

			// 编写ocr识别提示词
			String ocrContent = String.format("""
					读取发票中的金额和发票编号等关键信息
					%s
					""", converter.getFormat());

			List<Media> mediaList = List
				.of(new Media(MimeTypeUtils.IMAGE_JPEG, new URI(OCR_IMAGE_FILE_URL).toURL().toURI()));

			UserMessage userMessage = UserMessage.builder()
				.text(messageDTO.getContent())
				.media(mediaList)
				.metadata(new HashMap<>())
				.build();
			// 设置文件格式
			userMessage.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);

			Prompt prompt = new Prompt(userMessage, DashScopeChatOptions.builder()
				// 选择支持图片识别的模型
				.withModel("qwen-vl-max-latest")
				.withMultiModel(Boolean.TRUE)
				.build());

			ChatResponse flux = ChatClient
				// 自定义使用不同的大模型
				.create(dashScopeChatModel)
				.prompt(prompt)
				.call()
				.chatResponse();

			InvoiceOCR convert = converter.convert(String.join("",
					Objects.requireNonNull(Objects.requireNonNull(flux).getResult().getOutput().getText())));
			MessageVO messageVO = new MessageVO();
			messageVO.setContent(JacksonUtils.toJson(convert));
			return Flux.just(messageVO);

		}
		catch (Exception e) {
			log.error("创建OCR识别失败", e);
			throw new TWTException("创建OCR识别失败");
		}
	}

	/**
	 * 文生图
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public Flux<MessageVO> ttiChatStream(MessageDTO messageDTO) {
		ImageMessage userMessage = new ImageMessage(messageDTO.getContent());

		DashScopeImageOptions dashScopeImageOptions = DashScopeImageOptions.builder()
			// 选择支持图片识别的模型
			.withModel("flux-schnell")
			.withWidth(1024)
			.withHeight(1024)
			.build();

		ImagePrompt prompt = new ImagePrompt(userMessage, dashScopeImageOptions);

		ImageResponse response = dashScopeImageModel.call(prompt);

		MessageVO messageVO = new MessageVO();
		messageVO.setContent(JacksonUtils.toJson(response.getResult()));

		return Flux.just(messageVO);
	}

	/**
	 * 文生视频
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public Flux<MessageVO> itvChatStream(MessageDTO messageDTO) {
		return null;
	}

	/**
	 * tts文字转语音
	 * @param ttsDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public SpeechSynthesisOutput tts(TtsDTO ttsDTO) {
		AiModel aiModel = aiModelMapper.selectAiModelByModelDefault(ModelEnums.ModelTypeEnums.TTS);
		SpeechSynthesisModel dashScopeSpeechSynthesisModel = ModelUtils
			.modelServiceProviderSelector(aiModel.getModelProvider())
			.getDashScopeTTSModel(aiModel);

		SpeechSynthesisMessage speechSynthesisMessage = new SpeechSynthesisMessage(ttsDTO.getContent());

		SpeechSynthesisPrompt speechSynthesisPrompt = new SpeechSynthesisPrompt(speechSynthesisMessage);

		SpeechSynthesisResponse response = dashScopeSpeechSynthesisModel.call(speechSynthesisPrompt);

		return response.getResult().getOutput();
	}

	/**
	 * stt语音转文字
	 * @param sttDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public String stt(SttDTO sttDTO) {
		DashScopeAudioTranscriptionOptions dashScopeAudioTranscriptionOptions = DashScopeAudioTranscriptionOptions
			.builder()
			.withModel("paraformer-v2")
			.build();

		AudioTranscriptionPrompt audioTranscriptionPrompt = new AudioTranscriptionPrompt(
				// TODO 不支持直接的文件流，只能上传到OSS传入地址的方式
				sttDTO.getAudio().getResource(), dashScopeAudioTranscriptionOptions);

		AudioTranscriptionResponse response = dashScopeAudioTranscriptionModel.call(audioTranscriptionPrompt);

		return response.getResult().getOutput();
	}

}
