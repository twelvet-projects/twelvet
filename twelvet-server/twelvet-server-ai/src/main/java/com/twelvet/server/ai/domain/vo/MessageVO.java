package com.twelvet.server.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AI助手聊天VO")
public class MessageVO {

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
		return "MessageVO{" + "content='" + content + '\'' + '}';
	}

}
