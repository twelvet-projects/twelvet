package com.twelvet.server.ai.model;

import com.alibaba.cloud.ai.model.RerankModel;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.framework.core.exception.TWTException;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.core.Ordered;

/**
 * 大模型服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-06-14
 */
public abstract class ModelService implements Ordered {

	/**
	 * 重写此方法，以此判断是否支持此供应商
	 * @param modelProviderEnums 目标客户端
	 * @return true/false
	 */
	public abstract boolean support(ModelEnums.ModelProviderEnums modelProviderEnums);

	/**
	 * 获取聊天模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	public ChatModel getChatModel(AiModel aiModel) {
		throw new TWTException(
				String.format("%s does not support this ChatModel", aiModel.getModelProvider().getDesc()));
	}

	/**
	 * 获取向量模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	public EmbeddingModel getEmbeddingModel(AiModel aiModel) {
		throw new TWTException(
				String.format("%s does not support this EmbeddingModel", aiModel.getModelProvider().getDesc()));
	}

	/**
	 * 获取排序模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	public RerankModel getRerankModel(AiModel aiModel) {
		throw new TWTException(
				String.format("%s does not support this RerankModel", aiModel.getModelProvider().getDesc()));
	}

}
