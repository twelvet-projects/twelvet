package com.twelvet.api.ai.domain.vo;

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
 * AI聊天记录对象 ai_chat_history
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@Schema(description = "AI聊天记录对象VO")
public class AiChatHistoryVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 发送消息用户类型
	 */
	@Schema(description = "发送消息用户类型")
	@ExcelProperty(value = "发送消息用户类型")
	private RAGEnums.UserTypeEnums createByType;

	/**
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	@ExcelProperty(value = "消息内容")
	private String content;

	public RAGEnums.UserTypeEnums getCreateByType() {
		return createByType;
	}

	public void setCreateByType(RAGEnums.UserTypeEnums createByType) {
		this.createByType = createByType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "AiChatHistoryVO{" + "createByType=" + createByType + ", content='" + content + '\'' + '}';
	}

}
