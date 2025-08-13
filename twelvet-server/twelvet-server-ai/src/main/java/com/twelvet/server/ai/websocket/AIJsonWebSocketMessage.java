package com.twelvet.server.ai.websocket;

import com.twelvet.api.ai.domain.dto.TtsDTO;
import com.twelvet.framework.websocket.message.AbstractJsonWebSocketMessage;
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
public class AIJsonWebSocketMessage extends AbstractJsonWebSocketMessage {

	private TtsDTO tts;

	public TtsDTO getTts() {
		return tts;
	}

	public void setTts(TtsDTO tts) {
		this.tts = tts;
	}

	/**
	 * 构造函数，创建一个 Ping 类型的 JSON WebSocket 消息。
	 */
	public AIJsonWebSocketMessage() {
		super(WebSocketMessageTypeEnums.AI.getValue());
	}

}
