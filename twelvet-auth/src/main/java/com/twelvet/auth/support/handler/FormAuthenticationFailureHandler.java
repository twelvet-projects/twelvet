package com.twelvet.auth.support.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 表单登录失败处理逻辑
 */

public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFailureHandler.class);

	/**
	 * Called when an authentication attempt fails.
	 * @param request the request during which the authentication attempt occurred.
	 * @param response the response.
	 * @param exception the exception which was thrown to reject the authentication
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		log.debug("表单登录失败:{}", exception.getLocalizedMessage());
		String url = HttpUtil.encodeParams(String.format("/token/login?error=%s", exception.getMessage()),
				CharsetUtil.CHARSET_UTF_8);
		try {
			ServletUtils.getResponse().sendRedirect(url);
		}
		catch (IOException e) {
			throw new RuntimeException("重定向失败");
		}
	}

}
