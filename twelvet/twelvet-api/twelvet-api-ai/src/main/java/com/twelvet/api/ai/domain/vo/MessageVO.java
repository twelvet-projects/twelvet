package com.twelvet.api.ai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AI助手聊天VO")
public class MessageVO {

	/**
	 * 消息唯一ID
	 */
	@Schema(description = "消息唯一ID")
	private String msgId;

	/**
	 * 问题内容
	 */
	@Schema(description = "问题内容")
	private String content;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MessageVO{" + "msgId='" + msgId + '\'' + ", content='" + content + '\'' + '}';
	}

}
