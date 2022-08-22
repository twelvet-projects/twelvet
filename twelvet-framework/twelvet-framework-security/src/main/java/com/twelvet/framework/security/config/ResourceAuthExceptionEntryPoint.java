package com.twelvet.framework.security.config;

import cn.hutool.http.HttpStatus;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.utils.CharsetKit;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 资源服务器异常处理
 */
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = LoggerFactory.getLogger(ResourceAuthExceptionEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		try {
			response.setCharacterEncoding(CharsetKit.UTF_8);
			R<String> result = new R<>();
			result.setCode(HttpStatus.HTTP_UNAUTHORIZED);
			int code = HttpStatus.HTTP_UNAUTHORIZED;
			if (authException != null) {
				result.setMsg("error");
				result.setData(authException.getMessage());
			}

			// 针对令牌过期返回特殊的 424
			if (authException instanceof InsufficientAuthenticationException) {
				code = org.springframework.http.HttpStatus.FAILED_DEPENDENCY.value();
				result.setMsg("token expire");
			}

			ServletUtils.render(code, JacksonUtils.getInstance().writeValueAsString(result));
		}
		catch (Exception e) {
			log.error("鉴权返回错误失败");
		}
	}

}