package com.twelvet.server.ai.model.impl;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
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
import org.springframework.stereotype.Component;

/**
 * 阿里百练大模型服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-06-14
 */
@Component
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

	@Override
	public ChatModel getTTSModel(AiModel aiModel) {
		return super.getTTSModel(aiModel);
	}

	/**
	 * 获取项链模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	@Override
	public EmbeddingModel getEmbeddingModel(AiModel aiModel) {
		DashScopeApi dashScopeApi = DashScopeApi.builder().apiKey(aiModel.getApiKey()).build();
		DashScopeEmbeddingOptions dashScopeEmbeddingOptions = DashScopeEmbeddingOptions.builder()
			.withModel(aiModel.getModel())
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
