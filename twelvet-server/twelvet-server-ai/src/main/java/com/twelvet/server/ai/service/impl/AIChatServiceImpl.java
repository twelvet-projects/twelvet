package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeSpeechSynthesisApi;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionOptions;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.*;
import com.alibaba.cloud.ai.dashscope.audio.transcription.AudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.chat.MessageFormat;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.yitter.idgen.YitIdHelper;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.api.ai.domain.AiKnowledge;
import com.twelvet.api.ai.domain.dto.*;
import com.twelvet.api.ai.domain.ocr.InvoiceOCR;
import com.twelvet.api.ai.domain.vo.AiChatHistoryVO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.server.ai.constant.AIDataSourceConstants;
import com.twelvet.server.ai.constant.RAGConstants;
import com.twelvet.server.ai.fun.MockWeatherService;
import com.twelvet.server.ai.fun.vo.Request;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.mapper.AiKnowledgeMapper;
import com.twelvet.server.ai.service.AIChatService;
import com.twelvet.server.ai.service.IAiChatHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.model.Content;
import org.springframework.ai.model.Media;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

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

	/**
	 * 文字model
	 */
	private final DashScopeChatModel dashScopeChatModel;

	/**
	 * 图像model
	 */
	private final DashScopeImageModel dashScopeImageModel;

	/**
	 * 语音转文字model
	 */
	private final DashScopeAudioTranscriptionModel dashScopeAudioTranscriptionModel;

	/**
	 * TTS model
	 */
	private final DashScopeSpeechSynthesisModel dashScopeSpeechSynthesisModel;

	private final VectorStore vectorStore;

	private final AiKnowledgeMapper aiKnowledgeMapper;

	private final AiDocSliceMapper aiDocSliceMapper;

	private final IAiChatHistoryService aiChatHistoryService;

	private final SpeechSynthesisModel speechSynthesisModel;

	private final AudioTranscriptionModel audioTranscriptionModel;

	private final SensitiveWordBs sensitiveWordBs;

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

	public AIChatServiceImpl(DashScopeChatModel dashScopeChatModel, VectorStore vectorStore,
			AiKnowledgeMapper aiKnowledgeMapper, AiDocSliceMapper aiDocSliceMapper,
			IAiChatHistoryService aiChatHistoryService, SpeechSynthesisModel speechSynthesisModel,
			AudioTranscriptionModel audioTranscriptionModel, SensitiveWordBs sensitiveWordBs,
			DashScopeImageModel dashScopeImageModel, DashScopeAudioTranscriptionModel dashScopeAudioTranscriptionModel,
			DashScopeSpeechSynthesisModel dashScopeSpeechSynthesisModel) {
		this.dashScopeChatModel = dashScopeChatModel;
		this.vectorStore = vectorStore;
		this.aiKnowledgeMapper = aiKnowledgeMapper;
		this.aiDocSliceMapper = aiDocSliceMapper;
		this.aiChatHistoryService = aiChatHistoryService;
		this.speechSynthesisModel = speechSynthesisModel;
		this.audioTranscriptionModel = audioTranscriptionModel;
		this.sensitiveWordBs = sensitiveWordBs;
		this.dashScopeImageModel = dashScopeImageModel;
		this.dashScopeAudioTranscriptionModel = dashScopeAudioTranscriptionModel;
		this.dashScopeSpeechSynthesisModel = dashScopeSpeechSynthesisModel;
	}

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
			SearchRequest searchRequest = SearchRequest
				// 搜索向量内容
				.query(messageDTO.getContent())
				// 向量匹配最多条数
				.withTopK(aiKnowledge.getTopK())
				// 匹配相似度准度
				.withSimilarityThreshold(aiKnowledge.getScore())
				// 过滤元数据
				.withFilterExpression(filter);

			return vectorStore.similaritySearch(searchRequest);
		}, TUtils.threadPoolExecutor);

		CompletableFuture.allOf(vectorCompletableFuture, messagesCompletableFuture).join();

		List<Document> docs = vectorCompletableFuture.join();
		List<Message> messages = messagesCompletableFuture.join();

		if (CollectionUtil.isNotEmpty(docs)) {
			// 获取切片ID
			List<Long> sliceIdList = new ArrayList<>();
			for (Document document : docs) {
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
			String documentContext = docs.stream()
				.map(Content::getContent)
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
				// TODO 需要等待官方解决BUG
				List<Media> mediaList = List
					.of(new Media(MimeTypeUtils.IMAGE_PNG, new URI(MULTI_IMAGE_FILE_URL).toURL()));
				UserMessage userMessage = new UserMessage(messageDTO.getContent(), mediaList);
				// 设置文件格式
				userMessage.getMetadata().put(DashScopeChatModel.MESSAGE_FORMAT, MessageFormat.IMAGE);

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

		DashScopeChatOptions dashScopeChatOptions = DashScopeChatOptions.builder()
			.withModel("qwen-max")
			// 开启联网搜索会对function造成影响
			.withEnableSearch(Boolean.FALSE)
			// 关联注册方法
			// .withFunction("mockWeatherService")
			.build();

		if (Boolean.TRUE.equals(messageDTO.getInternetFlag())) { // 是否开启联网
			dashScopeChatOptions.setEnableSearch(Boolean.TRUE);
		}

		return ChatClient
			// 自定义使用不同的大模型
			.create(dashScopeChatModel)
			.prompt(prompt)
			// 功能选择
			.options(dashScopeChatOptions)
			// 注册function
			.function("mockWeatherService", "根据城市查询天气", Request.class, new MockWeatherService())
			.stream()
			.chatResponse()
			.map(chatResponse -> {
				MessageVO messageVO = new MessageVO();
				String content = chatResponse.getResult().getOutput().getContent();
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
	 * 多模态回答用户问题
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	@Override
	public Flux<MessageVO> multiChatStream(MessageDTO messageDTO) {

		try {
			UserMessage userMessage;
			if (Boolean.TRUE) { // 图片
				List<Media> mediaList = List
					.of(new Media(MimeTypeUtils.IMAGE_JPEG, new URI(MULTI_IMAGE_FILE_URL).toURL()));
				userMessage = new UserMessage(messageDTO.getContent(), mediaList);
				// 设置文件格式
				userMessage.getMetadata().put(DashScopeChatModel.MESSAGE_FORMAT, MessageFormat.IMAGE);
			}
			else { // 视频
					// TODO 目前非真正的视频识别，以下实现不可用，官方例子为把视频切成图片进行识别
				List<Media> mediaList = List
					.of(new Media(MimeTypeUtils.IMAGE_JPEG, new URI(MULTI_VIDEO_FILE_URL).toURL()));
				userMessage = new UserMessage(messageDTO.getContent(), mediaList);
				// 设置文件格式
				userMessage.getMetadata().put(DashScopeChatModel.MESSAGE_FORMAT, MessageFormat.VIDEO);
			}

			Prompt prompt = new Prompt(userMessage, DashScopeChatOptions.builder()
				// 选择支持图片识别的模型
				.withModel("qwen-vl-max-latest")
				.withMultiModel(Boolean.TRUE)
				.build());

			ChatResponse response = ChatClient
				// 自定义使用不同的大模型
				.create(dashScopeChatModel)
				.prompt(prompt)
				.call()
				.chatResponse();

			MessageVO messageVO = new MessageVO();
			messageVO.setContent(response.getResult().getOutput().getContent());
			return Flux.just(messageVO);

		}
		catch (Exception e) {
			log.error("创建多模态提问失败", e);
			throw new TWTException("创建多模态提问失败");
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

			List<Media> mediaList = List.of(new Media(MimeTypeUtils.IMAGE_JPEG, new URI(OCR_IMAGE_FILE_URL).toURL()));
			UserMessage userMessage = new UserMessage(ocrContent, mediaList);
			// 设置文件格式
			userMessage.getMetadata().put(DashScopeChatModel.MESSAGE_FORMAT, MessageFormat.IMAGE);

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

			InvoiceOCR convert = converter
				.convert(String.join("", Objects.requireNonNull(flux.getResult().getOutput().getContent())));
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
		SpeechSynthesisMessage speechSynthesisMessage = new SpeechSynthesisMessage(ttsDTO.getContent());

		DashScopeSpeechSynthesisOptions dashScopeSpeechSynthesisOptions = DashScopeSpeechSynthesisOptions.builder()
			// 不同模型可能不支持字级别音素边界
			.withModel("sambert-zhimiao-emo-v1")
			.withResponseFormat(DashScopeSpeechSynthesisApi.ResponseFormat.WAV)
			.withEnableWordTimestamp(Boolean.TRUE)
			.withEnablePhonemeTimestamp(Boolean.TRUE)
			.build();

		SpeechSynthesisPrompt speechSynthesisPrompt = new SpeechSynthesisPrompt(speechSynthesisMessage,
				dashScopeSpeechSynthesisOptions);

		// TODO 采用的websocket请求，存在BUG关闭后无法再使用
		SpeechSynthesisResponse response = speechSynthesisModel.call(speechSynthesisPrompt);

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

		AudioTranscriptionResponse response = audioTranscriptionModel.call(audioTranscriptionPrompt);

		return response.getResult().getOutput();
	}

}
