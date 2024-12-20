package com.twelvet.api.ai.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.constant.RAGEnums;
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

	/**
	 * ID
	 */
	@Schema(description = "ID")
	private Long chatHistoryId;

	/**
	 * 消息唯一id
	 */
	@Schema(description = "消息唯一id")
	@ExcelProperty(value = "消息唯一id")
	private String msgId;

	/**
	 * 归属的消息用户ID
	 */
	@Schema(description = "归属的消息用户ID")
	@ExcelProperty(value = "归属的消息用户ID")
	private String userId;

	/**
	 * 知识库ID
	 */
	@Schema(description = "知识库ID")
	@ExcelProperty(value = "知识库ID")
	private Long knowledgeId;

	/**
	 * 消息发送人ID
	 */
	@Schema(description = "消息发送人ID")
	@ExcelProperty(value = "消息发送人ID")
	private String sendUserId;

	/**
	 * 消息发送人名称
	 */
	@Schema(description = "消息发送人名称")
	@ExcelProperty(value = "消息发送人名称")
	private String sendUserName;

	/**
	 * 发送消息用户类型
	 */
	@Schema(description = "发送消息用户类型")
	@ExcelProperty(value = "发送消息用户类型")
	private RAGEnums.UserTypeEnums createByType;

	/**
	 * 1:已删除，0：未删除
	 */
	@Schema(description = "1:已删除，0：未删除")
	private Boolean delFlag;

	public Long getChatHistoryId() {
		return chatHistoryId;
	}

	public void setChatHistoryId(Long chatHistoryId) {
		this.chatHistoryId = chatHistoryId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public RAGEnums.UserTypeEnums getCreateByType() {
		return createByType;
	}

	public void setCreateByType(RAGEnums.UserTypeEnums createByType) {
		this.createByType = createByType;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("chatHistoryId", getChatHistoryId())
			.append("msgId", getMsgId())
			.append("userId", getUserId())
			.append("knowledgeId", getKnowledgeId())
			.append("sendUserId", getSendUserId())
			.append("sendUserName", getSendUserName())
			.append("createByType", getCreateByType())
			.append("createTime", getCreateTime())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
