package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrievalAdvisor;
import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.server.ai.service.AIChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

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
    private DashScopeChatModel chatModel;

    @Autowired
    private VectorStore vectorStore;

    /**
     * 发起聊天
     *
     * @param messageDTO MessageDTO
     * @return 流式数据返回
     */
    @Override
    public Flux<MessageVO> chatStream(MessageDTO messageDTO) {
        // 指定过滤元数据
        FilterExpressionBuilder filterExpressionBuilder = new FilterExpressionBuilder();
        // 从向量数据库中搜索相似文档
        Filter.Expression filter = filterExpressionBuilder
                .eq("modelId", 1)
                .build();
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest
                        // 搜索向量内容
                        .query(messageDTO.getContent())
                        // 向量匹配最多条数
                        .withTopK(5)
                        // 匹配相似度准度
                        .withSimilarityThreshold(SearchRequest.SIMILARITY_THRESHOLD_ACCEPT_ALL)
                        // 过滤元数据
                        .withFilterExpression(filter)
        );

        if (CollectionUtil.isNotEmpty(docs)) {
            for (Document document : docs) {
                Map<String, Object> metadata = document.getMetadata();
                log.info(metadata.toString());
                // TODO 储存命中次数
            }
        } else { // 无法匹配返回找不到相关信息
            MessageVO messageVO = new MessageVO();
            messageVO.setContent("知识库未匹配相关问题，请重新提问");
            return Flux.just(messageVO);
        }

        // 获取documents里的content
        List<String> context = docs.stream().map(Document::getContent).toList();
        // 创建系统提示词
        SystemPromptTemplate promptTemplate = new SystemPromptTemplate("""
                Context information is below.
                ---------------------
                {context}
                ---------------------
                Given the context information and not prior knowledge, answer the question.
                You need to respond with content in context first, and then respond with your own database. When the given context doesn't help you answer the question, just say "I don't know."
                """);
        // 填充数据
        Message systemMessage = promptTemplate.createMessage(Map.of("context", context));

        // 用户发起提问
        UserMessage userMessage = new UserMessage(messageDTO.getContent());
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return chatModel
                .stream(prompt)
                .map(chatResponse -> {
                    MessageVO messageVO = new MessageVO();
                    messageVO.setContent(chatResponse.getResult().getOutput().getContent());
                    return messageVO;
                });
    }

}
