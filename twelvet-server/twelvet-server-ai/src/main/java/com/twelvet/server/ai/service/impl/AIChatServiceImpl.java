package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.yitter.idgen.YitIdHelper;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.api.ai.domain.dto.AiChatHistoryDTO;
import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.dto.SearchAiChatHistoryDTO;
import com.twelvet.api.ai.domain.vo.AiChatHistoryVO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.server.ai.constant.AIDataSourceConstants;
import com.twelvet.server.ai.constant.RAGConstants;
import com.twelvet.server.ai.fun.MockWeatherService;
import com.twelvet.server.ai.fun.vo.ActorsFilms;
import com.twelvet.server.ai.fun.vo.Request;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.mapper.AiModelMapper;
import com.twelvet.server.ai.service.AIChatService;
import com.twelvet.server.ai.service.IAiChatHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.model.Content;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

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

	private final DashScopeChatModel dashScopeChatModel;

	private final VectorStore vectorStore;

	private final AiModelMapper aiModelMapper;

	private final AiDocSliceMapper aiDocSliceMapper;

	private final IAiChatHistoryService aiChatHistoryService;

	public AIChatServiceImpl(DashScopeChatModel dashScopeChatModel, VectorStore vectorStore,
			AiModelMapper aiModelMapper, AiDocSliceMapper aiDocSliceMapper,
			IAiChatHistoryService aiChatHistoryService) {
		this.dashScopeChatModel = dashScopeChatModel;
		this.vectorStore = vectorStore;
		this.aiModelMapper = aiModelMapper;
		this.aiDocSliceMapper = aiDocSliceMapper;
		this.aiChatHistoryService = aiChatHistoryService;
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

		AiModel aiModel = aiModelMapper.selectAiModelByModelId(messageDTO.getModelId());
		if (Objects.isNull(aiModel)) {
			throw new TWTException("此知识库不存在");
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
					searchAiChatHistoryDTO.setMultiRound(aiModel.getMultiRound());
					searchAiChatHistoryDTO.setModelId(aiModel.getModelId());
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
				userAIChatHistoryDTO.setModelId(aiModel.getModelId());
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
				.eq(RAGEnums.VectorMetadataEnums.MODEL_ID.getCode(), aiModel.getModelId())
				.build();
			SearchRequest searchRequest = SearchRequest
				// 搜索向量内容
				.query(messageDTO.getContent())
				// 向量匹配最多条数
				.withTopK(aiModel.getTopK())
				// 匹配相似度准度
				.withSimilarityThreshold(SearchRequest.SIMILARITY_THRESHOLD_ACCEPT_ALL)
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
				Long sliceId = (Long) metadata.get(RAGEnums.VectorMetadataEnums.SLICE_ID.getCode());
				if (Objects.nonNull(sliceId) && !sliceIdList.contains(sliceId)) {
					sliceIdList.add(sliceId);
				}
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
			MessageVO messageVO = new MessageVO();
			messageVO.setContent("知识库未匹配相关问题，请重新提问");
			// return Flux.just(messageVO);
		}

		// 储存AI回答
		// 回复时间必须保证在用户提问时间之前（重新获取时间，并且增加1毫秒），保证排序
		LocalDateTime replyNow = LocalDateTime.now().plusNanos(1_000_000);
		// 生成唯一消息雪花ID
		String aiMsgId = String.valueOf(YitIdHelper.nextId());
		// ai回复内容
		StringBuffer aiContent = new StringBuffer();

		Prompt prompt = new Prompt(messages);
		return ChatClient
			// 自定义使用不同的大模型
			.create(dashScopeChatModel)
			.prompt(prompt)
			// 功能选择
			// .options(
			// DashScopeChatOptions.builder()
			// // 开启联网搜索
			// .withEnableSearch(true).build()
			// )
			.user(promptUserSpec -> {
				// 用户提问文本
				promptUserSpec.text(messageDTO.getContent());

				// 用户提问图片/语音
				// promptUserSpec.media();
			})
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
					aiChatHistoryDTO.setModelId(aiModel.getModelId());
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

}
