package com.twelvet.framework.websocket.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 消息数据对象，分发信息VO
 * </p>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-11
 */
public class MessageVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 是否需要广播消息。
	 */
	private Boolean broadcastFlag;

	/**
	 * 目标会话的唯一标识列表。
	 */
	private List<Object> sessionKeys;

	/**
	 * 需要发送的消息文本内容。
	 */
	private String messageText;

	public Boolean getBroadcastFlag() {
		return broadcastFlag;
	}

	public void setBroadcastFlag(Boolean broadcastFlag) {
		this.broadcastFlag = broadcastFlag;
	}

	public List<Object> getSessionKeys() {
		return sessionKeys;
	}

	public void setSessionKeys(List<Object> sessionKeys) {
		this.sessionKeys = sessionKeys;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * 构建一个需要广播的消息对象。
	 */
	public static MessageVO broadcastMessage(String text) {
		MessageVO messageVO = new MessageVO();
		messageVO.setMessageText(text);
		messageVO.setBroadcastFlag(true);
		return messageVO;
	}

	/**
	 * 构建一个指定发送对象。
	 */
	public static MessageVO sessionKeysMessage(String text, List<Object> sessionKeys) {
		MessageVO messageVO = new MessageVO();
		messageVO.setMessageText(text);
		messageVO.setSessionKeys(sessionKeys);
		return messageVO;
	}

}
