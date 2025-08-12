package com.twelvet.framework.websocket.distribute;

import com.twelvet.framework.websocket.domain.vo.MessageVO;

/**
 * <p>
 * 实现本地消息分发
 * </p>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-11
 */
public class LocalMessageDistributor implements MessageDistributor, MessageSender {

	/**
	 * 分发消息，对于本地分发器，直接调用发送逻辑。
	 * @param messageDO 待发送的消息对象，包含消息内容和目标会话信息。
	 */
	@Override
	public void distribute(MessageVO messageDO) {
		doSend(messageDO);
	}

}
