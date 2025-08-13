package com.twelvet.framework.websocket.utils;

import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.websocket.constants.WebSocketConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 安全工具类，WebSocket专用
 */
public class SecurityWebSocketUtils {

	/**
	 * 获取Authentication
	 * @return Authentication
	 */
	public static LoginUser getAuthentication(WebSocketSession session) {
		if (Objects.isNull(session)) {
			return null;
		}
		Object authentication = session.getAttributes().get(WebSocketConstants.USER_KEY_ATTR_NAME);
		if (Objects.isNull(authentication)) {
			return null;
		}
		return (LoginUser) authentication;
	}

	/**
	 * 获取用户
	 * @return String
	 */
	public static String getUsername(WebSocketSession session) {
		return getLoginUser(session).getUsername();
	}

	/**
	 * 获取用户
	 * @return LoginUser
	 */
	public static LoginUser getLoginUser(WebSocketSession session) {
		LoginUser loginUser = getAuthentication(session);
		if (Objects.isNull(loginUser)) {
			throw new TWTException("获取用户信息主体失败");
		}
		return loginUser;
	}

}
