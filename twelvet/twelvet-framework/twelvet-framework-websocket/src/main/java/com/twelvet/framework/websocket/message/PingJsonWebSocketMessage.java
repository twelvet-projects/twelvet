package com.twelvet.framework.websocket.message;

import com.twelvet.framework.websocket.constants.enums.WebSocketMessageTypeEnums;

/**
 * Ping JSON WebSocket 消息
 * <p>
 * 表示一个 Ping 类型的 WebSocket 消息，通常用于客户端向服务器发送心跳请求。
 * </p>
 *
 * @author Hccake 2021/1/4
 * @version 1.0
 */
public class PingJsonWebSocketMessage extends AbstractJsonWebSocketMessage {

	/**
	 * 构造函数，创建一个 Ping 类型的 JSON WebSocket 消息。
	 */
	public PingJsonWebSocketMessage() {
		super(WebSocketMessageTypeEnums.PING.getValue());
	}

}
