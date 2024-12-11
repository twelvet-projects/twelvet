package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.api.ai.domain.dto.AiChatHistoryDTO;
import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
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
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.Message;
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

    private final DashScopeChatModel chatModel;

    private final VectorStore vectorStore;

    private final AiMessageChatMemory aiMessageChatMemory;

    private final AiModelMapper aiModelMapper;

    private final AiDocSliceMapper aiDocSliceMapper;

    private final IAiChatHistoryService aiChatHistoryService;

    public AIChatServiceImpl(DashScopeChatModel chatModel, VectorStore vectorStore,
                             AiMessageChatMemory aiMessageChatMemory, AiModelMapper aiModelMapper, AiDocSliceMapper aiDocSliceMapper,
                             IAiChatHistoryService aiChatHistoryService) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
        this.aiMessageChatMemory = aiMessageChatMemory;
        this.aiModelMapper = aiModelMapper;
        this.aiDocSliceMapper = aiDocSliceMapper;
        this.aiChatHistoryService = aiChatHistoryService;
    }

    /**
     * 发起聊天
     *
     * @param messageDTO MessageDTO
     * @return 流式数据返回
     */
    @Override
    public Flux<MessageVO> chatStream(MessageDTO messageDTO) {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        AiModel aiModel = aiModelMapper.selectAiModelByModelId(messageDTO.getModelId());
        if (Objects.isNull(aiModel)) {
            throw new TWTException("此知识库不存在");
        }

        LocalDateTime userNow = LocalDateTime.now();
        String userId = String.valueOf(loginUser.getUserId());
        // 储存用户提问
        AiChatHistoryDTO userAIChatHistoryDTO = new AiChatHistoryDTO();
        // TODO 生成唯一消息ID
        userAIChatHistoryDTO.setMsgId("");
        userAIChatHistoryDTO.setUserId(userId);
        userAIChatHistoryDTO.setSendUserId(userId);
        userAIChatHistoryDTO.setSendUserName(loginUser.getUsername());
        // 设置消息归属人
        userAIChatHistoryDTO.setCreateByType(RAGEnums.UserTypeEnums.USER);
        userAIChatHistoryDTO.setContent(messageDTO.getContent());
        userAIChatHistoryDTO.setCreateTime(userNow);
        aiChatHistoryService.insertAiChatHistory(userAIChatHistoryDTO);


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

        List<Document> docs = vectorStore.similaritySearch(searchRequest);

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

        } else { // 无法匹配返回找不到相关信息
            MessageVO messageVO = new MessageVO();
            messageVO.setContent("知识库未匹配相关问题，请重新提问");
            // return Flux.just(messageVO);
        }

        // 获取documents里的content
        String documentContext = docs.stream()
                .map(Content::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
        // 创建系统提示词
        SystemPromptTemplate promptTemplate = new SystemPromptTemplate("""
                Context information is below.
                ---------------------
                {question_answer_context}
                ---------------------
                Given the context and provided history information and not prior knowledge,
                reply to the user comment. If the answer is not in the context, inform
                the user that you can't answer the question.
                """);
        // 填充数据
        Message systemMessage = promptTemplate.createMessage(Map.of("question_answer_context", documentContext));

        // 用户发起提问
        // UserMessage userMessage = new UserMessage(messageDTO.getContent());
        // TODO 加入历史对话，或实现下方
        Prompt prompt = new Prompt(List.of(systemMessage));

        // TODO 储存AI回答
        // 回复时间必须保证在用户提问时间之前（重新获取时间，并且增加1毫秒），保证排序
        LocalDateTime replyNow = LocalDateTime.now().plusNanos(1_000_000);

        // ai回复内容
        StringBuffer aiContent = new StringBuffer();

        return ChatClient
                // 自定义使用不同的大模型
                .create(chatModel)
                .prompt(prompt)
                .user(promptUserSpec -> {
                    // 用户提问文本
                    promptUserSpec.text(messageDTO.getContent());

                    // 用户提问图片/语音
                    // promptUserSpec.media();
                })
                .advisors(advisorSpec -> {
                    // 使用历史消息
                    useChatHistory(advisorSpec, "1");

                    // 使用向量数据库
                    // useVectorStore(advisorSpec, searchRequest);
                })
                .stream()
                .chatResponse()
                .map(chatResponse -> {
                    MessageVO messageVO = new MessageVO();
                    String content = chatResponse.getResult().getOutput().getContent();
                    messageVO.setContent(content);
                    // 储存AI回复内容
                    aiContent.append(content);
                    return messageVO;
                })
                .doFinally(signalType -> {
                    if (
                            Arrays.asList(SignalType.CANCEL, SignalType.ON_COMPLETE)
                                    .contains(signalType)
                    ) { // 取消链接时或完成输出时
                        // 储存AI提问
                        AiChatHistoryDTO aiChatHistoryDTO = new AiChatHistoryDTO();
                        // TODO 生成唯一消息ID
                        aiChatHistoryDTO.setMsgId("");
                        aiChatHistoryDTO.setUserId(userId);
                        aiChatHistoryDTO.setSendUserId(userId);
                        aiChatHistoryDTO.setSendUserName(loginUser.getUsername());
                        // 设置消息归属人
                        aiChatHistoryDTO.setCreateByType(RAGEnums.UserTypeEnums.AI);
                        aiChatHistoryDTO.setContent(aiContent.toString());
                        aiChatHistoryDTO.setCreateTime(replyNow);
                        aiChatHistoryService.insertAiChatHistory(aiChatHistoryDTO);
                    }
                });
    }

    /**
     * 格式化输出
     *
     * @param messageDTO
     * @return
     */
    @Override
    public Flux<MessageVO> formatTest(MessageDTO messageDTO) {
        BeanOutputConverter<List<ActorsFilms>> converter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<ActorsFilms>>() {
                });

        return ChatClient
                // 自定义使用不同的大模型
                .create(chatModel)
                .prompt()
                .user(u -> u.text("""
                          Generate the filmography for a random {actor}.
                          {format}
                        """).param("actor", messageDTO.getContent()).param("format", converter.getFormat()))
                .stream()
                .chatResponse()
                .map(chatResponse -> {
                    MessageVO messageVO = new MessageVO();
                    messageVO.setContent(chatResponse.getResult().getOutput().getContent());
                    return messageVO;
                });
    }

    /**
     * 工具调用
     *
     * @param messageDTO
     * @return
     */
    @Override
    public Flux<MessageVO> funTest(MessageDTO messageDTO) {
        return ChatClient
                // 自定义使用不同的大模型
                .create(chatModel)
                .prompt()
                .function("mockWeatherService", "根据城市查询天气", Request.class, new MockWeatherService())
                .user(messageDTO.getContent())
                .stream()
                .chatResponse()
                .map(chatResponse -> {
                    MessageVO messageVO = new MessageVO();
                    messageVO.setContent(chatResponse.getResult().getOutput().getContent());
                    return messageVO;
                });
    }

    /**
     * 用户历史对话
     *
     * @param advisorSpec ChatClient.AdvisorSpec
     * @param sessionId   String
     */
    public void useChatHistory(ChatClient.AdvisorSpec advisorSpec, String sessionId) {
        // 1. 如果需要存储会话和消息到数据库，自己可以实现ChatMemory接口，这里使用自己实现的AiMessageChatMemory，数据库存储。
        // 2. 传入会话id，MessageChatMemoryAdvisor会根据会话id去查找消息。
        // 3. 只需要携带最近10条消息
        // MessageChatMemoryAdvisor会在消息发送给大模型之前，从ChatMemory中获取会话的历史消息，然后一起发送给大模型。
        advisorSpec.advisors(new MessageChatMemoryAdvisor(aiMessageChatMemory, sessionId, 10));
    }

    /**
     * 创建向量搜索提示词
     *
     * @param advisorSpec   AdvisorSpec
     * @param searchRequest SearchRequest
     */
    public void useVectorStore(ChatClient.AdvisorSpec advisorSpec, SearchRequest searchRequest) {
        // question_answer_context是一个占位符，会替换成向量数据库中查询到的文档。QuestionAnswerAdvisor会替换。
        String promptWithContext = """
                下面是上下文信息
                ---------------------
                {question_answer_context}
                ---------------------
                给定的上下文和提供的历史信息，而不是事先的知识，回复用户的意见。如果答案不在上下文中，告诉用户你不能回答这个问题。
                """;
        advisorSpec.advisors(new QuestionAnswerAdvisor(vectorStore, searchRequest, promptWithContext));
    }

}
