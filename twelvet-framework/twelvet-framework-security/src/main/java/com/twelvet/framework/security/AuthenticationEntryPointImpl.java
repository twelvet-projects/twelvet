package com.twelvet.framework.security;

import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.utils.http.ServletUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 认证失败处理类 返回未授权
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException authenticationException) throws IOException {

		// 获取异常主体
		Throwable cause = authenticationException.getCause();

		// 异常代码401,用户没有权限（令牌、用户名、密码错误）
		int code = HttpStatus.UNAUTHORIZED.value();
		// 异常信息
		String msg;

		// 判断异常类型(token失效)
		if (cause instanceof InvalidTokenException) {
			msg = "Invalid token";
		}
		else {
			msg = "Sorry, You don't have access";
		}

		ObjectMapper objectMapper = new ObjectMapper();

		// 发送json数据
		ServletUtils.render(code, objectMapper.writeValueAsString(AjaxResult.error(code, msg)));

	}

}