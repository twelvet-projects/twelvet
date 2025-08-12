package com.twelvet.framework.websocket.distribute;

import cn.hutool.core.collection.CollectionUtil;
import com.twelvet.framework.websocket.domain.vo.MessageVO;

import java.util.List;

/**
 * <p>
 * 消息发送
 * </p>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-11
 */
public interface MessageSender {

	/**
	 * 执行消息发送
	 * @param messageVO 待发送的消息对象，包含消息内容和目标会话信息。
	 */
	default void doSend(MessageVO messageVO) {
		Boolean broadcastFlag = messageVO.getBroadcastFlag();
		String messageText = messageVO.getMessageText();
		List<Object> sessionKeys = messageVO.getSessionKeys();
		if (Boolean.TRUE.equals(broadcastFlag)) {
			// 广播信息
			WebSocketMessageSender.broadcast(messageText);
		}
		else if (CollectionUtil.isNotEmpty(sessionKeys)) {
			// 指定用户发送
			for (Object sessionKey : sessionKeys) {
				WebSocketMessageSender.send(sessionKey, messageText);
			}
		}
	}

}
