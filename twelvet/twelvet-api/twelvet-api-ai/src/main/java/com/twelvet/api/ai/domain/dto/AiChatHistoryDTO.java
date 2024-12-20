package com.twelvet.api.ai.domain.dto;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.constant.RAGEnums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 插入AI聊天记录对象 ai_chat_history
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@Schema(description = "插入AI聊天记录对象DTO")
public class AiChatHistoryDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

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
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	@ExcelProperty(value = "消息内容")
	private String content;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@ExcelProperty("创建时间")
	private LocalDateTime createTime;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AiChatHistoryDTO{" + "msgId='" + msgId + '\'' + ", userId='" + userId + '\'' + ", knowledgeId="
				+ knowledgeId + ", sendUserId='" + sendUserId + '\'' + ", sendUserName='" + sendUserName + '\''
				+ ", createByType=" + createByType + ", content='" + content + '\'' + ", createTime=" + createTime
				+ '}';
	}

}
