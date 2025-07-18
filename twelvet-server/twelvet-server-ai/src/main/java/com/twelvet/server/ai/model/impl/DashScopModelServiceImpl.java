package com.twelvet.server.ai.model.impl;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeSpeechSynthesisApi;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import com.alibaba.cloud.ai.dashscope.rerank.DashScopeRerankModel;
import com.alibaba.cloud.ai.dashscope.rerank.DashScopeRerankOptions;
import com.alibaba.cloud.ai.model.RerankModel;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.server.ai.model.ModelService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

/**
 * 阿里百练大模型服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-06-14
 */
@Service
public class DashScopModelServiceImpl extends ModelService {

	@Override
	public boolean support(ModelEnums.ModelProviderEnums modelProviderEnums) {
		return ModelEnums.ModelProviderEnums.DASHSCOPE.equals(modelProviderEnums);
	}

	/**
	 * 获取聊天模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	@Override
	public ChatModel getChatModel(AiModel aiModel) {
		DashScopeApi dashScopeApi = DashScopeApi.builder()
			.baseUrl(aiModel.getBaseUrl())
			.apiKey(aiModel.getApiKey())
			.build();
		DashScopeChatOptions dashScopeChatOptions = DashScopeChatOptions.builder()
			.withModel(aiModel.getModel())
			.withTopP(aiModel.getTopP())
			.withTemperature(aiModel.getTemperature())
			.build();

		// TODO 需要更换入参进行控制
		/*
		 * if (Boolean.TRUE.equals(messageDTO.getInternetFlag())) { // 是否开启联网
		 * dashScopeChatOptions.setEnableSearch(Boolean.TRUE); }
		 */
		return DashScopeChatModel.builder().dashScopeApi(dashScopeApi).defaultOptions(dashScopeChatOptions).build();
	}

	/**
	 * 获取阿里文字转语音模型模型
	 * @param aiModel AiModel
	 * @return SpeechSynthesisModel
	 */
	@Override
	public SpeechSynthesisModel getDashScopeTTSModel(AiModel aiModel) {
		DashScopeSpeechSynthesisOptions dashScopeSpeechSynthesisOptions = DashScopeSpeechSynthesisOptions.builder()
			// 不同模型可能不支持字级别音素边界
			.model(aiModel.getModel())
			// TODO 以下参数需要进行自定义
			.responseFormat(DashScopeSpeechSynthesisApi.ResponseFormat.WAV)
			.enableWordTimestamp(Boolean.TRUE)
			.enablePhonemeTimestamp(Boolean.TRUE)
			.build();

		DashScopeSpeechSynthesisApi dashScopeSpeechSynthesisApi = new DashScopeSpeechSynthesisApi(aiModel.getApiKey(),
				null, aiModel.getBaseUrl());

		return new DashScopeSpeechSynthesisModel(dashScopeSpeechSynthesisApi, dashScopeSpeechSynthesisOptions);
	}

	/**
	 * 获取项链模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	@Override
	public EmbeddingModel getEmbeddingModel(AiModel aiModel) {
		DashScopeApi dashScopeApi = DashScopeApi.builder()
			.apiKey(aiModel.getApiKey())
			.baseUrl(aiModel.getBaseUrl())
			.build();
		DashScopeEmbeddingOptions dashScopeEmbeddingOptions = DashScopeEmbeddingOptions.builder()
			.withModel(DashScopeApi.DEFAULT_EMBEDDING_MODEL)
			.withTextType(DashScopeApi.DEFAULT_EMBEDDING_TEXT_TYPE)
			.build();
		return new DashScopeEmbeddingModel(dashScopeApi, MetadataMode.EMBED, dashScopeEmbeddingOptions);
	}

	/**
	 * 获取排序模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	@Override
	public RerankModel getRerankModel(AiModel aiModel) {
		DashScopeApi dashScopeApi = DashScopeApi.builder()
			.baseUrl(aiModel.getBaseUrl())
			.apiKey(aiModel.getApiKey())
			.build();
		DashScopeRerankOptions scopeRerankOptions = DashScopeRerankOptions.builder()
			.withModel(aiModel.getModel())
			.build();
		return new DashScopeRerankModel(dashScopeApi, scopeRerankOptions);
	}

	/**
	 * 最小的顺序
	 * @return int
	 */
	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
