package com.twelvet;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.server.ai.AiApplication;
import com.twelvet.server.ai.mapper.AiModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * <p>
 * 模型评估测试
 * <p>
 *
 * @since 2025/011
 */
@SpringBootTest(classes = AiApplication.class)
public class ModelTest {

	@Autowired
	private AiModelMapper aiModelMapper;

	/**
	 * 向量数据库
	 */
	@Autowired(required = false)
	private VectorStore vectorStore;

	/**
	 * 相关性评估
	 */
	@Test
	public void relevancyEvaluatorTest() {
		// 用户提问
		String question = "twelvet是什么?";

		ChatModel chatModel = getChatModel();

		// 获取模型响应
		ChatResponse chatResponse = ChatClient.builder(chatModel)
			.build()
			.prompt(question)
			// 支持上下文测试
			.advisors(getVectorStore())
			.call()
			.chatResponse();

		Assertions.assertNotNull(chatResponse);
		EvaluationRequest evaluationRequest = new EvaluationRequest(
				// The original user question
				question,
				// The retrieved context from the RAG flow
				chatResponse.getMetadata().get(RetrievalAugmentationAdvisor.DOCUMENT_CONTEXT),
				// The AI model's response
				chatResponse.getResult().getOutput().getText());

		// 发起模型评测
		String evaluatorPrompt = """
				您的任务是评估查询的响应
				与所提供的上下文信息一致。

				你有两个答案。是或否。

				如果查询的答案为“是”
				与上下文信息一致，否则为否。

				Query:
				{query}

				Response:
				{response}

				Context:
				{context}

				Answer:
				""";
		PromptTemplate promptTemplate = new PromptTemplate(evaluatorPrompt);
		RelevancyEvaluator evaluator = new RelevancyEvaluator(ChatClient.builder(chatModel));
		EvaluationResponse evaluationResponse = evaluator.evaluate(evaluationRequest);

		assertThat(evaluationResponse.isPass()).isTrue();
	}

	/**
	 * 事实评估
	 */
	@Test
	public void factCheckingEvaluatorTest() {

		ChatModel chatModel = getChatModel();

		ChatClient.Builder builder = ChatClient.builder(chatModel);

		// Create the FactCheckingEvaluator
		var factCheckingEvaluator = new FactCheckingEvaluator(builder, """
					Evaluate whether or not the following claim is supported by the provided document.
					Respond with "yes" if the claim is supported, or "no" if it is not.
					Document: \\n {document}\\n
					Claim: \\n {claim}
				""");

		// Example context and claim
		String document = "地球是距离太阳第三远的行星，也是唯一已知存在生命的天体。";
		String claim = "地球是离太阳第四远的行星。";

		// Create an EvaluationRequest
		EvaluationRequest evaluationRequest = new EvaluationRequest(document, Collections.emptyList(), claim);

		// Perform the evaluation
		EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);

		assertFalse(evaluationResponse.isPass(), "这一说法不应得到上下文的支持");
	}

	/**
	 * 获取模型
	 * @return ChatModel
	 */
	private ChatModel getChatModel() {
		AiModel aiModel = aiModelMapper.selectAiModelByModelDefault(ModelEnums.ModelTypeEnums.LLM);

		DashScopeApi dashScopeApi = DashScopeApi.builder()
			.baseUrl(aiModel.getBaseUrl())
			.apiKey(aiModel.getApiKey())
			.build();
		DashScopeChatOptions dashScopeChatOptions = DashScopeChatOptions.builder()
			.withModel(aiModel.getModel())
			.withTopP(aiModel.getTopP())
			.withTemperature(aiModel.getTemperature())
			.build();

		return DashScopeChatModel.builder().dashScopeApi(dashScopeApi).defaultOptions(dashScopeChatOptions).build();
	}

	/**
	 * 获取向量数据库信息
	 */
	private RetrievalAugmentationAdvisor getVectorStore() {
		return RetrievalAugmentationAdvisor.builder()
			.documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
			.build();
	}

}
