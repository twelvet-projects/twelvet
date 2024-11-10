package com.twelvet.server.ai.controller;

import com.alibaba.cloud.ai.advisor.DocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.server.ai.domian.MessageVO;
import com.twelvet.server.ai.domian.params.MessageParams;
import com.twelvet.server.ai.mapper.TestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

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
    private DashScopeApi dashscopeApi;

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private TestMapper testMapper;


    @Operation(summary = "回答用户问题")
    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageVO> genAnswer(@RequestBody MessageParams messageParams) {
        DocumentRetriever retriever = new DashScopeDocumentRetriever(dashscopeApi, DashScopeDocumentRetrieverOptions.builder()
                .withIndexName("twelvet")
                .build());


        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(new DocumentRetrievalAdvisor(retriever))
                .build();

        return chatClient
                .prompt()
                .user(messageParams.getContent())
                .stream()
                .chatResponse()
                .map(
                        chatResponse -> {
                            MessageVO messageVO = new MessageVO();
                            messageVO.setContent(chatResponse.getResult().getOutput().getContent());
                            // 获取引用的文档
                            List<Document> documents = (List<Document>) chatResponse.getMetadata().get(DashScopeDocumentRetrievalAdvisor.RETRIEVED_DOCUMENTS);
                            return messageVO;
                        }
                );
        /*return chatModel.stream(new Prompt(new UserMessage(messageParams.getContent()))).map(chatResponse -> {
            MessageVO messageVO = new MessageVO();
            messageVO.setContent(chatResponse.getResult().getOutput().getContent());
            return messageVO;
        });*/
    }

    @AuthIgnore(value = false)
    @Operation(summary = "向量化文本")
    @GetMapping("/embedding")
    public JsonResult embedding(@RequestParam String answer) {
        // 2. 文档向量化
        List<Document> docs = List.of(
                new Document(answer)
        );

         vectorStore.add(docs);


         // 会自动调用向量化模型，向量化后再插入数据库
        //vectorStore.add(docs);
        return JsonResult.success();
    }

    @AuthIgnore(value = false)
    @Operation(summary = "向量搜索")
    @GetMapping("/search")
    public List<Document> search(@RequestParam String message) {
        return vectorStore.similaritySearch(message);

        /*return vectorStore.similaritySearch(SearchRequest
                .query(message)
                .withTopK(2));*/
    }

    @AuthIgnore(value = false)
    @Operation(summary = "测试mysql数据库")
    @GetMapping("/test-mysql")
    public void testMysql(){
        List<Map<String, Object>> maps = testMapper.selectI18nByI18nId();
        System.out.println(maps);

    }

}
