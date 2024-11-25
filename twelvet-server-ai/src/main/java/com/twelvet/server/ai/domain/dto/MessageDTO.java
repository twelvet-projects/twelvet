package com.twelvet.server.ai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

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

	/**
	 * 问题内容
	 */
	@Schema(description = "问题内容")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MessageParams{" + "content='" + content + '\'' + '}';
	}

}
