package com.twelvet.api.ai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "STT语音转文字DTO")
public class SttDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@NotNull(message = "音频文件不允许为空")
	@Schema(description = "音频文件")
	private MultipartFile audio;

	public @NotNull(message = "音频文件不允许为空") MultipartFile getAudio() {
		return audio;
	}

	public void setAudio(@NotNull(message = "音频文件不允许为空") MultipartFile audio) {
		this.audio = audio;
	}

	@Override
	public String toString() {
		return "SttDTO{" + "audio=" + audio + '}';
	}

}
