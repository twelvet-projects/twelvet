package com.twelvet.api.ai.domain.dto;

import com.twelvet.api.ai.constant.RAGEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: AI助手聊天params
 */
@Schema(description = "AI助手聊天params")
public class MessageDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@NotNull(message = "知识库ID不能为空")
	@Schema(description = "知识库ID")
	private Long knowledgeId;

	@NotNull(message = "聊天内容类型")
	@Schema(description = "聊天内容类型")
	private RAGEnums.ChatTypeEnums chatType;

	@NotBlank(message = "提问内容不能为空")
	@Schema(description = "提问内容")
	private String content;

	@Schema(description = "是否携带上下文记忆")
	private Boolean carryContextFlag;

	@Schema(description = "是否联网")
	private Boolean internetFlag;

	public @NotNull(message = "知识库ID不能为空") Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(@NotNull(message = "知识库ID不能为空") Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public @NotNull(message = "聊天内容类型") RAGEnums.ChatTypeEnums getChatType() {
		return chatType;
	}

	public void setChatType(@NotNull(message = "聊天内容类型") RAGEnums.ChatTypeEnums chatType) {
		this.chatType = chatType;
	}

	public @NotBlank(message = "提问内容不能为空") String getContent() {
		return content;
	}

	public void setContent(@NotBlank(message = "提问内容不能为空") String content) {
		this.content = content;
	}

	public Boolean getCarryContextFlag() {
		return carryContextFlag;
	}

	public void setCarryContextFlag(Boolean carryContextFlag) {
		this.carryContextFlag = carryContextFlag;
	}

	public Boolean getInternetFlag() {
		return internetFlag;
	}

	public void setInternetFlag(Boolean internetFlag) {
		this.internetFlag = internetFlag;
	}

	@Override
	public String toString() {
		return "MessageDTO{" + "knowledgeId=" + knowledgeId + ", chatType=" + chatType + ", content='" + content + '\''
				+ ", carryContextFlag=" + carryContextFlag + ", internetFlag=" + internetFlag + '}';
	}

}
