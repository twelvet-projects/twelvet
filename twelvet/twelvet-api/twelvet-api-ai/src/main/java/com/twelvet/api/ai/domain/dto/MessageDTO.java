package com.twelvet.api.ai.domain.dto;

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

	@NotBlank(message = "提问内容不能为空")
	@Schema(description = "提问内容")
	private String content;

	@Schema(description = "是否携带上下文记忆")
	private Boolean carryContextFlag;

	public @NotNull(message = "知识库ID不能为空") Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(@NotNull(message = "知识库ID不能为空") Long knowledgeId) {
		this.knowledgeId = knowledgeId;
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

	@Override
	public String toString() {
		return "MessageDTO{" + "knowledgeId=" + knowledgeId + ", content='" + content + '\'' + ", carryContextFlag="
				+ carryContextFlag + '}';
	}

}
