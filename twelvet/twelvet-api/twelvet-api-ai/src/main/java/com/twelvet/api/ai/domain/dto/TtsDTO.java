package com.twelvet.api.ai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "TTS文字转语音DTO")
public class TtsDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Length(max = 150, message = "内容最大长度为150")
	@NotBlank(message = "内容不能为空")
	@Schema(description = "内容")
	private String content;

	public @NotBlank(message = "提问内容不能为空") String getContent() {
		return content;
	}

	public void setContent(@NotBlank(message = "提问内容不能为空") String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TtsDTO{" + "content='" + content + '\'' + '}';
	}

}
