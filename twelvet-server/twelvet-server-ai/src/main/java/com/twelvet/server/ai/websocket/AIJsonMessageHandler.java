package com.twelvet.server.ai.websocket;

import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisOutput;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.websocket.distribute.WebSocketMessageSender;
import com.twelvet.framework.websocket.handler.JsonMessageHandler;
import com.twelvet.framework.websocket.message.PingJsonWebSocketMessage;
import com.twelvet.framework.websocket.constants.enums.WebSocketMessageTypeEnums;
import com.twelvet.server.ai.service.AIChatService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * AI消息处理器
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-13
 */
@Service
public class AIJsonMessageHandler implements JsonMessageHandler<AIJsonWebSocketMessage> {

	private final AIChatService aiChatService;

	public AIJsonMessageHandler(AIChatService aiChatService) {
		this.aiChatService = aiChatService;
	}

	/**
	 * 处理用户发送的消息
	 * @param session 当前的 WebSocket 会话。
	 * @param message 接收到用户消息。
	 */
	@Override
	public void handle(WebSocketSession session, AIJsonWebSocketMessage message) {
		SpeechSynthesisOutput tts = aiChatService.tts(message.getTts());
		WebSocketMessageSender.send(session, JacksonUtils.toJson(tts));
	}

	/**
	 * 获取此处理器处理的消息类型。
	 * @return 返回 {@link WebSocketMessageTypeEnums#PING} 的值。
	 */
	@Override
	public String type() {
		return WebSocketMessageTypeEnums.AI.getValue();
	}

	/**
	 * 获取此处理器对应的消息 Class。
	 * @return 返回 {@link PingJsonWebSocketMessage} 的 Class 对象。
	 */
	@Override
	public Class<AIJsonWebSocketMessage> getMessageClass() {
		return AIJsonWebSocketMessage.class;
	}

}
