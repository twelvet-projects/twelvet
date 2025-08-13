package com.twelvet.framework.websocket.constants.enums;

/**
 * WebSocket 消息类型枚举
 * <p>
 * 定义了 WebSocket 消息的常见类型，例如 Ping 和 Pong，用于心跳机制。
 * </p>
 *
 * @author Hccake 2021/1/4
 * @version 1.0
 */
public enum WebSocketMessageTypeEnums {

	/**
	 * Ping 消息类型，通常由客户端发送，用于心跳检测。
	 */
	PING("ping"),

	/**
	 * Pong 消息类型，通常由服务器响应 Ping 消息发送，用于心跳检测。
	 */
	PONG("pong"),

	/**
	 * 消息类型：AI服务消息处理器
	 */
	AI("ai"),

	;

	private final String value;

	WebSocketMessageTypeEnums(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
