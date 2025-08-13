package com.twelvet.framework.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twelvet.framework.websocket.holder.JsonMessageHandlerHolder;
import com.twelvet.framework.websocket.message.AbstractJsonWebSocketMessage;
import com.twelvet.framework.websocket.message.JsonWebSocketMessage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;

/**
 * 自定义 WebSocket 处理器
 * <p>
 * 继承自 {@link TextWebSocketHandler}，负责处理接收到的文本 WebSocket 消息。 它能够根据消息内容（JSON
 * 格式或纯文本）分发给不同的消息处理器。
 * </p>
 *
 * @author Hccake 2020/12/31
 * @version 1.0
 */
public class CustomTextWebSocketHandler extends TextWebSocketHandler {

	private final static Logger log = LoggerFactory.getLogger(CustomTextWebSocketHandler.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		// 有特殊需要转义字符, 不报错
		MAPPER.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
	}

	private PlanTextMessageHandler planTextMessageHandler;

	/**
	 * 构造函数，创建一个不带纯文本消息处理器的 {@code CustomWebSocketHandler} 实例。
	 */
	public CustomTextWebSocketHandler() {
	}

	/**
	 * 构造函数，创建一个带纯文本消息处理器的 {@code CustomWebSocketHandler} 实例。
	 * @param planTextMessageHandler 用于处理非 JSON 格式的纯文本消息。
	 */
	public CustomTextWebSocketHandler(PlanTextMessageHandler planTextMessageHandler) {
		this.planTextMessageHandler = planTextMessageHandler;
	}

	/**
	 * 处理接收到的文本 WebSocket 消息。
	 * <p>
	 * 如果消息是 JSON 格式且包含 'type' 字段，则根据 'type' 查找并调用对应的 {@link JsonMessageHandler}。
	 * 如果消息是纯文本或不包含 'type' 字段，则尝试使用 {@link PlanTextMessageHandler} 处理。
	 * </p>
	 * @param session 当前的 WebSocket 会话。
	 * @param message 接收到的文本消息。
	 * @throws JsonProcessingException 如果 JSON 消息处理失败。
	 */
	@Override
	public void handleTextMessage(@NotNull WebSocketSession session, TextMessage message)
			throws JsonProcessingException {
		// 空消息不处理
		if (message.getPayloadLength() == 0) {
			return;
		}

		// 消息类型必有一属性type，先解析，获取该属性
		String payload = message.getPayload();
		JsonNode jsonNode = MAPPER.readTree(payload);
		JsonNode typeNode = jsonNode.get(AbstractJsonWebSocketMessage.TYPE_FIELD);

		if (Objects.isNull(typeNode)) {
			if (Objects.nonNull(planTextMessageHandler)) {
				planTextMessageHandler.handle(session, payload);
			}
			else {
				log.error("[handleTextMessage] 普通文本消息（{}）没有对应的消息处理器", payload);
			}
		}
		else {
			String messageType = typeNode.asText();
			// 获得对应的消息处理器
			JsonMessageHandler jsonMessageHandler = JsonMessageHandlerHolder.getHandler(messageType);
			if (Objects.isNull(jsonMessageHandler)) {
				log.error("[handleTextMessage] 消息类型（{}）不存在对应的消息处理器", messageType);
				return;
			}
			// 消息处理
			Class<? extends JsonWebSocketMessage> messageClass = jsonMessageHandler.getMessageClass();
			JsonWebSocketMessage websocketMessageJson = MAPPER.treeToValue(jsonNode, messageClass);
			jsonMessageHandler.handle(session, websocketMessageJson);
		}
	}

}
