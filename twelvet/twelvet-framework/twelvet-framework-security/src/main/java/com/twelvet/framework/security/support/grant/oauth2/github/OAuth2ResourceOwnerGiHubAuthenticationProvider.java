package com.twelvet.framework.security.support.grant.oauth2.github;

import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import com.twelvet.framework.security.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import com.twelvet.framework.security.support.grant.oauth2.github.constant.SecurityOauth2Constants;
import com.twelvet.framework.security.support.service.TwUserDetailsService;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 处理用户名密码授权
 */
public class OAuth2ResourceOwnerGiHubAuthenticationProvider
		extends OAuth2ResourceOwnerBaseAuthenticationProvider<OAuth2ResourceOwnerGitHubAuthenticationToken> {

	private static final Logger log = LogManager.getLogger(OAuth2ResourceOwnerGiHubAuthenticationProvider.class);

	private final AuthRequest authRequest;

	/**
	 * Constructs an {@code OAuth2AuthorizationCodeAuthenticationProvider} using the
	 * provided parameters.
	 * @param authenticationManager AuthenticationManager
	 * @param authorizationService the authorization service
	 * @param tokenGenerator the token generator
	 * @since 0.2.3
	 */
	public OAuth2ResourceOwnerGiHubAuthenticationProvider(AuthenticationManager authenticationManager,
			OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
			AuthRequest authRequest) {
		super(authenticationManager, authorizationService, tokenGenerator);
		this.authRequest = authRequest;
	}

	/**
	 * 获取已获得授权的AuthenticationToken
	 * @param authentication 构建参数
	 * @return Authentication
	 */
	@Override
	public Authentication buildAuthenticationToken(Authentication authentication) {
		OAuth2ResourceOwnerGitHubAuthenticationToken oAuth2ResourceOwnerPasswordAuthenticationToken = (OAuth2ResourceOwnerGitHubAuthenticationToken) authentication;
		Map<String, Object> additionalParameters = oAuth2ResourceOwnerPasswordAuthenticationToken
			.getAdditionalParameters();
		String code = (String) additionalParameters.get(SecurityOauth2Constants.CODE);
		String state = (String) additionalParameters.get(SecurityOauth2Constants.STATE);

		Map<String, TwUserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(TwUserDetailsService.class);

		String clientId = oAuth2ResourceOwnerPasswordAuthenticationToken.getClientPrincipal().getName();
		String grantType = oAuth2ResourceOwnerPasswordAuthenticationToken.getAuthorizationGrantType().getValue();

		// 获取第三方登录信息
		AuthCallback authCallback = AuthCallback.builder().code(code).state(state).build();
		AuthResponse<AuthUser> authUserAuthResponse = authRequest.login(authCallback);
		AuthUser authUser = authUserAuthResponse.getData();

		// 获取需要使用的登录器
		Optional<TwUserDetailsService> optional = userDetailsServiceMap.values()
			.stream()
			.filter(service -> service.support(clientId, grantType))
			.max(Comparator.comparingInt(Ordered::getOrder));

		if (optional.isEmpty()) {
			throw new InternalAuthenticationServiceException("UserDetailsService error , not register");
		}

		try {
			// GitHub唯一用户ID进行绑定登录
			UserDetails userDetails = optional.get()
				.loadUserByOAuth2UserId(Oauth2GrantEnums.GITHUB, authUser.getUuid());
			if (Objects.isNull(userDetails)) {
				log.debug("Failed to authenticate since no credentials provided");
				throw new BadCredentialsException(I18nUtils
					.getLocale("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}

			return new OAuth2ResourceOwnerGitHubAuthenticationToken(userDetails);
		}
		catch (AuthenticationException ex) {
			throw ex;
		}
		catch (Exception ex) {
			log.debug("Unknown login error thrown");
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		boolean supports = OAuth2ResourceOwnerGitHubAuthenticationToken.class.isAssignableFrom(authentication);
		log.debug("supports authentication=" + authentication + " returning " + supports);
		return supports;
	}

	@Override
	public void checkClient(RegisteredClient registeredClient) {
		assert registeredClient != null;
		if (!registeredClient.getAuthorizationGrantTypes()
			.contains(new AuthorizationGrantType(Oauth2GrantEnums.PASSWORD.getGrant()))) {
			throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
		}
	}

	/**
	 * 自定义校验登录，采用不同的登录方式
	 * @param authentication the authentication request object.
	 * @return Authentication
	 * @throws AuthenticationException AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return super.authenticate(authentication);
	}

}
