package com.twelvet.framework.websocket.distribute;

import com.twelvet.framework.websocket.domain.vo.MessageVO;

/**
 * <p>
 * WebSocket 消息路由器， 用于实现集群消息分发（Redis、MQ）
 * </p>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-11
 */
public interface MessageDistributor {

	/**
	 * 分发消息。
	 * <p>
	 * 根据实现类的不同，此方法可以将消息发送到本地会话或发布到消息队列中， 以便在集群环境中进行广播或单点发送。
	 * @param messageVO MessageVO
	 * </p>
	 */
	void distribute(MessageVO messageVO);

}
