package com.twelvet.server.ai.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.server.ai.domian.MessageVO;
import com.twelvet.server.ai.domian.params.MessageParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * AI助手Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-10-26
 */
@Tag(description = "AIChatController", name = "AI助手")
@RestController
@RequestMapping("/chat")
public class AIChatController {

    private final static Logger log = LoggerFactory.getLogger(AIChatController.class);

    @Autowired
    private DashScopeChatModel chatModel;

    @Autowired
    private DashScopeEmbeddingModel embeddingModel;

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private DashScopeApi dashscopeApi;


    @Operation(summary = "回答用户问题")
    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageVO> genAnswer(@RequestBody MessageParams messageParams) {
        return chatModel.stream(new Prompt(new UserMessage(messageParams.getContent()))).map(chatResponse -> {
            MessageVO messageVO = new MessageVO();
            messageVO.setContent(chatResponse.getResult().getOutput().getContent());
            return messageVO;
        });
    }

    @AuthIgnore(value = false)
    @Operation(summary = "向量化文本")
    @GetMapping("/embedding")
    public void embedding(@RequestParam String answer) {
        List<Document> docs = List.of(
                new Document(answer)
        );
        // 会自动调用向量化模型，向量化后再插入数据库
        vectorStore.add(docs);

        /*float[] embeddingResponse = embeddingModel.embed(answer);

        log.info("向量化后的数据：{}", embeddingResponse);*/
    }

    @AuthIgnore(value = false)
    @Operation(summary = "向量搜索")
    @GetMapping("/search")
    public List<Document> search(@RequestParam String message) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest
                .query(message)
                .withTopK(2));
        return documents;
    }

}
