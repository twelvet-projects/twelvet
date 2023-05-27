package com.twelvet.framework.security.config;

import cn.hutool.http.HttpStatus;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.utils.CharsetKit;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 资源服务器异常处理
 */
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = LoggerFactory.getLogger(ResourceAuthExceptionEntryPoint.class);

	private final MessageSource messageSource;

	public ResourceAuthExceptionEntryPoint(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

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
			if (authException instanceof InvalidBearerTokenException
					|| authException instanceof InsufficientAuthenticationException) {
				code = HttpStatus.HTTP_OK;
				result
					.setMsg(this.messageSource.getMessage("OAuth2ResourceOwnerBaseAuthenticationProvider.tokenExpired",
							null, LocaleContextHolder.getLocale()));
			}

			ServletUtils.render(code, JacksonUtils.getInstance().writeValueAsString(result));
		}
		catch (Exception e) {
			log.error("鉴权返回错误失败", e);
		}
	}

}
