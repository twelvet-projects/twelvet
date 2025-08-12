package com.twelvet.framework.websocket.holder;

import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.websocket.constants.WebSocketConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * <p>
 * WebSocket Session 标识生成器
 * </p>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-11
 */
@Configuration
public class TwSessionKeyGenerator implements SessionKeyGenerator {

	/**
	 * 获取WebSocket会话中的唯一标识
	 * @param webSocketSession 当前的 WebSocket 会话对象。
	 * @return
	 */
	@Override
	public Object sessionKey(WebSocketSession webSocketSession) {

		Object obj = webSocketSession.getAttributes().get(WebSocketConstants.USER_KEY_ATTR_NAME);

		if (Objects.isNull(obj)) {
			return null;
		}

		if (obj instanceof LoginUser user) {
			// userId 作为唯一区分
			return String.valueOf(user.getUserId());
		}

		return null;
	}

}
