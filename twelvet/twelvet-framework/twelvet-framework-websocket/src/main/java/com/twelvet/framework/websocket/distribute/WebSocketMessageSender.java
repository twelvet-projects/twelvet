package com.twelvet.framework.websocket.distribute;

import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.websocket.holder.WebSocketSessionHolder;
import com.twelvet.framework.websocket.message.JsonWebSocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * WebSocket 消息发送器
 * <p>
 * 提供向 WebSocket 客户端发送消息的静态方法，支持广播和单点发送。
 * </p>
 *
 * @author Hccake 2021/1/4
 * @version 1.0
 */
public class WebSocketMessageSender {

	private final static Logger log = LoggerFactory.getLogger(WebSocketMessageSender.class);

	/**
	 * 向所有在线的 WebSocket 会话广播消息。
	 * @param message 要发送的消息文本。
	 */
	public static void broadcast(String message) {
		Collection<WebSocketSession> sessions = WebSocketSessionHolder.getSessions();
		for (WebSocketSession session : sessions) {
			send(session, message);
		}
	}

	/**
	 * 向指定会话标识的客户端发送消息。
	 * @param sessionKey 会话的唯一标识。
	 * @param message 要发送的消息文本。
	 * @return 如果找到会话并成功发送，返回 {@code true}；否则返回 {@code false}。
	 */
	public static boolean send(Object sessionKey, String message) {
		WebSocketSession session = WebSocketSessionHolder.getSession(sessionKey);
		if (Objects.isNull(session)) {
			log.info("[send] 当前 sessionKey：{} 对应 session 不在本服务中", sessionKey);
			return false;
		}
		else {
			return send(session, message);
		}
	}

	/**
	 * 向指定会话发送 JSON 格式的 WebSocket 消息。
	 * @param session WebSocket 会话。
	 * @param message 要发送的 JSON 消息对象。
	 */
	public static void send(WebSocketSession session, JsonWebSocketMessage message) {
		send(session, JacksonUtils.toJson(message));
	}

	/**
	 * 向指定会话发送文本消息。
	 * @param session WebSocket 会话。
	 * @param message 要发送的消息文本。
	 * @return 如果发送成功，返回 {@code true}；否则返回 {@code false}。
	 */
	public static boolean send(WebSocketSession session, String message) {
		if (Objects.isNull(session)) {
			log.error("[send] session 为 null");
			return false;
		}
		if (!session.isOpen()) {
			log.error("[send] session 已经关闭");
			return false;
		}
		try {
			session.sendMessage(new TextMessage(message));
		}
		catch (IOException e) {
			log.error("[send] session({}) 发送消息({}) 异常", session, message, e);
			return false;
		}
		return true;
	}

}
