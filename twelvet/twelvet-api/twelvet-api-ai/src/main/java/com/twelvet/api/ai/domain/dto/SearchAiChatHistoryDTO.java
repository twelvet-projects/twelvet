package com.twelvet.api.ai.domain.dto;

import cn.idev.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 搜索AI聊天记录对象DTO ai_chat_history
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@Schema(description = "搜索AI聊天记录对象DTO")
public class SearchAiChatHistoryDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 归属的消息用户ID
	 */
	@Schema(description = "归属的消息用户ID")
	@ExcelProperty(value = "归属的消息用户ID")
	private String userId;

	/**
	 * 知识库ID
	 */
	@Schema(description = "知识库ID")
	@ExcelProperty(value = "知识库ID")
	private Long modelId;

	/**
	 * 上下文记忆会话数
	 */
	@Schema(description = "上下文记忆会话数")
	@ExcelProperty(value = "上下文记忆会话数")
	private Integer multiRound;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Integer getMultiRound() {
		return multiRound;
	}

	public void setMultiRound(Integer multiRound) {
		this.multiRound = multiRound;
	}

	@Override
	public String toString() {
		return "SearchAiChatHistoryDTO{" + "userId='" + userId + '\'' + ", modelId=" + modelId + ", multiRound="
				+ multiRound + '}';
	}

}
