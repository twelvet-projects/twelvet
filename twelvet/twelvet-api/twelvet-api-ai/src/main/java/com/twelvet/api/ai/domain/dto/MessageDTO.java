package com.twelvet.api.ai.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
	private Long modelId;

	@NotBlank(message = "提问内容不能为空")
	@Schema(description = "提问内容")
	private String content;

	@Schema(description = "是否携带上下文记忆")
	private Boolean carryContextFlag;

	public @NotNull(message = "知识库ID不能为空") Long getModelId() {
		return modelId;
	}

	public void setModelId(@NotNull(message = "知识库ID不能为空") Long modelId) {
		this.modelId = modelId;
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
		return "MessageDTO{" + "modelId=" + modelId + ", content='" + content + '\'' + ", carryContextFlag="
				+ carryContextFlag + '}';
	}

}
