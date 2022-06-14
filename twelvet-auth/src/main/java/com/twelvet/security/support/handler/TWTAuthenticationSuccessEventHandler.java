package com.twelvet.security.support.handler;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.framework.log.event.SysLoginLogEvent;
import com.twelvet.framework.log.utils.SysLogUtils;
import com.twelvet.framework.log.vo.SysLogVO;
import com.twelvet.framework.security.constans.SecurityConstants;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.framework.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 
 * @date 2022-06-02
 */
public class TWTAuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(TWTAuthenticationSuccessEventHandler.class);

	private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

	/**
	 * Called when a user has been successfully authenticated.
	 * @param request the request which caused the successful authentication
	 * @param response the response
	 * @param authentication the <tt>Authentication</tt> object which was created during
	 * the authentication process.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		log.info("用户：{} 登录成功", authentication.getPrincipal());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String username = authentication.getName();
		SysLoginInfo sysLoginInfo = new SysLoginInfo();
		SysLogVO sysLog = SysLogUtils.getSysLog();
		sysLoginInfo.setStatus(SecurityConstants.LOGIN_SUCCESS);
		// 发送异步日志事件
		sysLoginInfo.setCreateTime(DateUtils.getNowDate());
		sysLoginInfo.setCreateBy(username);
		sysLoginInfo.setUpdateBy(username);
		SpringUtils.publishEvent(new SysLoginLogEvent(sysLoginInfo));

		// 输出token
		try {
			sendAccessTokenResponse(request, response, authentication);
		} catch (IOException e) {
			log.error("返回消息失败", e);
		}
	}

	private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

		OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
		OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

		OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
				.tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
		if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
			builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
		}
		if (refreshToken != null) {
			builder.refreshToken(refreshToken.getTokenValue());
		}
		if (!CollectionUtils.isEmpty(additionalParameters)) {
			builder.additionalParameters(additionalParameters);
		}
		OAuth2AccessTokenResponse accessTokenResponse = builder.build();
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

		// 无状态 注意删除 context 上下文的信息
		SecurityContextHolder.clearContext();
		this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
	}

}