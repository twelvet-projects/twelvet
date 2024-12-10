package com.twelvet.api.ai.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * AI聊天记录对象 ai_chat_history
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@Schema(description = "AI聊天记录对象")
public class AiChatHistory extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/** ID */
	@Schema(description = "ID")
	private Long chatHistoryId;

	/** 消息唯一id */
	@Schema(description = "消息唯一id")
	@ExcelProperty(value = "消息唯一id")
	private String msgId;

	/** 归属的消息用户ID */
	@Schema(description = "归属的消息用户ID")
	@ExcelProperty(value = "归属的消息用户ID")
	private String userId;

	/** 消息发送人ID */
	@Schema(description = "消息发送人ID")
	@ExcelProperty(value = "消息发送人ID")
	private String sendUserId;

	/** 消息发送人名称 */
	@Schema(description = "消息发送人名称")
	@ExcelProperty(value = "消息发送人名称")
	private String sendUserName;

	/** 发送消息用户类型 */
	@Schema(description = "发送消息用户类型")
	@ExcelProperty(value = "发送消息用户类型")
	private String createByType;

	/** 1:已删除，0：未删除 */
	@Schema(description = "1:已删除，0：未删除")
	private Integer delFlag;

	public void setChatHistoryId(Long chatHistoryId) {
		this.chatHistoryId = chatHistoryId;
	}

	public Long getChatHistoryId() {
		return chatHistoryId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setCreateByType(String createByType) {
		this.createByType = createByType;
	}

	public String getCreateByType() {
		return createByType;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("chatHistoryId", getChatHistoryId())
			.append("msgId", getMsgId())
			.append("userId", getUserId())
			.append("sendUserId", getSendUserId())
			.append("sendUserName", getSendUserName())
			.append("createByType", getCreateByType())
			.append("createTime", getCreateTime())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
