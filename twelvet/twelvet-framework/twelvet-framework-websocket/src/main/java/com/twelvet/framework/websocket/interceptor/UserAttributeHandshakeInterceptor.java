package com.twelvet.framework.websocket.interceptor;

import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.websocket.constants.WebSocketConstants;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * <p>
 * 用户属性握手拦截器
 * </p>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-11
 */
public class UserAttributeHandshakeInterceptor implements HandshakeInterceptor {

	/**
	 * 在 WebSocket 握手前调用，用于将用户信息添加到会话属性中。
	 * @param request 当前的服务器请求。
	 * @param response 当前的服务器响应。
	 * @param wsHandler 目标 WebSocket 处理器。
	 * @param attributes 用于存储 WebSocket 会话属性的映射。
	 * @return 始终返回 {@code true}，允许握手继续。
	 * @throws Exception 如果在处理过程中发生错误。
	 */
	@Override
	public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response,
			@NotNull WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		// 由于 WebSocket 握手是由 http 升级的，携带 access_token 会被识别为用户信息
		LoginUser user = SecurityUtils.getLoginUser();
		attributes.put(WebSocketConstants.USER_KEY_ATTR_NAME, user);
		return true;
	}

	/**
	 * 在 WebSocket 握手完成后调用。
	 * <p>
	 * 此方法在此实现中为空，没有执行任何操作。
	 * </p>
	 * @param request 当前的服务器请求。
	 * @param response 当前的服务器响应。
	 * @param wsHandler 目标 WebSocket 处理器。
	 * @param exception 握手过程中抛出的异常，如果没有异常则为 {@code null}。
	 */
	@Override
	public void afterHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response,
			@NotNull WebSocketHandler wsHandler, Exception exception) {

	}

}
