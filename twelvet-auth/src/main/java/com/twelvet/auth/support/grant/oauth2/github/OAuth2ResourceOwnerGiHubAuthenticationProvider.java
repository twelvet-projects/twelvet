package com.twelvet.auth.support.grant.oauth2.github;

import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import com.twelvet.framework.security.service.TwUserDetailsService;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.http.ServletUtils;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 处理用户名密码授权
 */
public class OAuth2ResourceOwnerGiHubAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOGGER = LogManager.getLogger(OAuth2ResourceOwnerGiHubAuthenticationProvider.class);

	private final UserDetailsChecker preAuthenticationChecks = new AccountStatusUserDetailsChecker();

	@Override
	public boolean supports(Class<?> authentication) {
		boolean supports = OAuth2ResourceOwnerGitHubAuthenticationToken.class.isAssignableFrom(authentication);
		LOGGER.debug("supports authentication=" + authentication + " returning " + supports);
		return supports;
	}

	/**
	 * 自定义校验登录，采用不同的登录方式
	 * @param authentication the authentication request object.
	 * @return Authentication
	 * @throws AuthenticationException AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			LOGGER.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException("Bad credentials");
		}

		OAuth2ResourceOwnerGitHubAuthenticationToken requestToken = (OAuth2ResourceOwnerGitHubAuthenticationToken) authentication;

		/* String grantType = requestToken.getAuthorizationGrantType().getValue(); */

		// 此处已获得 客户端认证 获取对应 userDetailsService
		Authentication clientAuthentication = SecurityContextHolder.getContext().getAuthentication();
		String clientId = clientAuthentication.getName();
		Map<String, TwUserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(TwUserDetailsService.class);
		Optional<TwUserDetailsService> optional = userDetailsServiceMap.values()
			.stream()
			.filter(service -> service.support("twelvet", "password"))
			.max(Comparator.comparingInt(Ordered::getOrder));

		if (optional.isEmpty()) {
			throw new InternalAuthenticationServiceException("UserDetailsService error , not register");
		}

		/*
		 * String code = (String) requestToken.getAdditionalParameters().get("code");
		 * String state = (String) requestToken.getAdditionalParameters().get("state");
		 */

		// TODO 删除代理
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "7890");
		System.setProperty("https.proxyHost", "127.0.0.1");
		System.setProperty("https.proxyPort", "7890");

		AuthGithubRequest authGithubRequest = SpringContextHolder.getBean(AuthGithubRequest.class);

		/*
		 * AuthResponse<AuthUser> authUser = authGithubRequest
		 * .login(AuthCallback.builder().code(code).state(state).build());
		 */

		UserDetails userDetails = optional.get().loadUserByUsername("admin");

		// 检查用户属性是否正常
		preAuthenticationChecks.check(userDetails);

		OAuth2ResourceOwnerGitHubAuthenticationToken oAuth2ResourceOwnerGitHubAuthenticationToken = new OAuth2ResourceOwnerGitHubAuthenticationToken(
				userDetails);
		oAuth2ResourceOwnerGitHubAuthenticationToken.setDetails(authentication.getDetails());

		return oAuth2ResourceOwnerGitHubAuthenticationToken;
	}

}
