package com.twelvet.server.ai.model;

import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.api.ai.domain.AiModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.core.Ordered;

/**
 * 大模型服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-06-14
 */
public interface ModelService extends Ordered {

	/**
	 * 重写此方法，以此判断是否支持此供应商
	 * @param modelProviderEnums 目标客户端
	 * @return true/false
	 */
	default boolean support(ModelEnums.ModelProviderEnums modelProviderEnums) {
		return true;
	}

	/**
	 * 获取聊天模型
	 * @param aiModel AiModel
	 * @return ChatModel
	 */
	ChatModel getChatModel(AiModel aiModel);

}
