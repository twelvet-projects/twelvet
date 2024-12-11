package com.twelvet.api.ai.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * AI会话内容详情对象 ai_chat_history_content
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@Schema(description = "AI会话内容详情对象")
public class AiChatHistoryContent extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/** ID */
	@Schema(description = "ID")
	private Long chatHistoryContentId;

	/** AI客服会话记录ID */
	@Schema(description = "AI客服会话记录ID")
	@ExcelProperty(value = "AI客服会话记录ID")
	private Long chatHistoryId;

	/** 消息内容 */
	@Schema(description = "消息内容")
	@ExcelProperty(value = "消息内容")
	private String content;

	public void setChatHistoryContentId(Long chatHistoryContentId) {
		this.chatHistoryContentId = chatHistoryContentId;
	}

	public Long getChatHistoryContentId() {
		return chatHistoryContentId;
	}

	public void setChatHistoryId(Long chatHistoryId) {
		this.chatHistoryId = chatHistoryId;
	}

	public Long getChatHistoryId() {
		return chatHistoryId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("chatHistoryContentId", getChatHistoryContentId())
			.append("chatHistoryId", getChatHistoryId())
			.append("content", getContent())
			.toString();
	}

}
