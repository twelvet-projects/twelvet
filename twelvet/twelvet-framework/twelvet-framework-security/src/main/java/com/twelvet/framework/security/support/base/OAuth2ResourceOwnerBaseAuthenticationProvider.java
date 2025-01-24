package com.twelvet.framework.security.support.base;

import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.security.constants.Oauth2ErrorConstants;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.exception.SmsCodeException;
import com.twelvet.framework.security.exception.UserFrozenException;
import com.twelvet.framework.security.utils.OAuth2ErrorCodesExpand;
import com.twelvet.framework.security.utils.ScopeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 处理自定义授权登录验证 注意：目前已经实现UserDetailsService的
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationProvider<T extends OAuth2ResourceOwnerBaseAuthenticationToken>
		implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(OAuth2ResourceOwnerBaseAuthenticationProvider.class);

	private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";

	private final OAuth2AuthorizationService authorizationService;

	private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

	private final AuthenticationManager authenticationManager;

	private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Deprecated
	private Supplier<String> refreshTokenGenerator;

	/**
	 * Constructs an {@code OAuth2AuthorizationCodeAuthenticationProvider} using the
	 * provided parameters.
	 * @param authorizationService the authorization service
	 * @param tokenGenerator the token generator
	 * @since 0.2.3
	 */
	public OAuth2ResourceOwnerBaseAuthenticationProvider(AuthenticationManager authenticationManager,
			OAuth2AuthorizationService authorizationService,
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
		Assert.notNull(authorizationService, "authorizationService cannot be null");
		Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
		this.authenticationManager = authenticationManager;
		this.authorizationService = authorizationService;
		this.tokenGenerator = tokenGenerator;
	}

	@Deprecated
	public void setRefreshTokenGenerator(Supplier<String> refreshTokenGenerator) {
		Assert.notNull(refreshTokenGenerator, "refreshTokenGenerator cannot be null");
		this.refreshTokenGenerator = refreshTokenGenerator;
	}

	/**
	 * 自定义获取用户信息并生成已获得授权的AuthenticationToken
	 * @param authentication 构建参数
	 * @return UsernamePasswordAuthenticationToken
	 */
	public abstract Authentication buildAuthenticationToken(Authentication authentication);

	/**
	 * 当前provider是否支持此令牌类型
	 * @param authentication Class<?>
	 * @return boolean
	 */
	@Override
	public abstract boolean supports(Class<?> authentication);

	/**
	 * 当前的请求客户端是否支持此模式
	 * @param registeredClient
	 */
	public abstract void checkClient(RegisteredClient registeredClient);

	/**
	 * 必须重写此方法，以此校验你的登录 Performs authentication with the same contract as
	 * {@link AuthenticationManager#authenticate(Authentication)} .
	 * @param authentication the authentication request object.
	 * @return a fully authenticated object including credentials. May return
	 * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
	 * authentication of the passed <code>Authentication</code> object. In such a case,
	 * the next <code>AuthenticationProvider</code> that supports the presented
	 * <code>Authentication</code> class will be tried.
	 * @throws AuthenticationException if authentication fails.
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		T resouceOwnerBaseAuthenticationScopes = (T) authentication;

		OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(
				resouceOwnerBaseAuthenticationScopes);

		RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
		checkClient(registeredClient);

		Set<String> authorizedScopes;
		// Default to configured scopes
		if (!CollectionUtils.isEmpty(resouceOwnerBaseAuthenticationScopes.getScopes())) {
			for (String requestedScope : resouceOwnerBaseAuthenticationScopes.getScopes()) {
				if (!registeredClient.getScopes().contains(requestedScope)) {
					throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
				}
			}
			authorizedScopes = new LinkedHashSet<>(resouceOwnerBaseAuthenticationScopes.getScopes());
		}
		else {
			authorizedScopes = new LinkedHashSet<>();
		}

		try {

			// 获取登录后的authenticateToken
			Authentication authenticationToken = buildAuthenticationToken(resouceOwnerBaseAuthenticationScopes);

			LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
			// 检查账号状态是否可用
			if (!loginUser.isAccountNonLocked()) {
				log.debug("User account is locked");
				throw new LockedException(I18nUtils.getLocale("AbstractUserDetailsAuthenticationProvider.locked",
						"User account is locked"));
			}
			if (!loginUser.isEnabled()) {
				log.debug("User account is disabled");
				throw new DisabledException(
						I18nUtils.getLocale("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
			}
			if (!loginUser.isAccountNonExpired()) {
				log.debug("User account is expired");
				throw new AccountExpiredException(I18nUtils
					.getLocale("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
			}

			log.debug("got authenticate=" + authenticationToken);

			// 会自动调用TwTUserDetailsServiceImpl.loadUserByUsername()
			/*
			 * Authentication usernamePasswordAuthentication = authenticationManager
			 * .authenticate(authenticationToken);
			 */

			AuthorizationGrantType authorizationGrantType = new AuthorizationGrantType(
					Oauth2GrantEnums.PASSWORD.getGrant());

			// @formatter:off
			DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
					.registeredClient(registeredClient)
					.principal(authenticationToken)
					.authorizationServerContext(AuthorizationServerContextHolder.getContext())
					.authorizedScopes(authorizedScopes)
					.authorizationGrantType(authorizationGrantType)
					.authorizationGrant(resouceOwnerBaseAuthenticationScopes);
			// @formatter:on

			OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization
				.withRegisteredClient(registeredClient)
				.principalName(authenticationToken.getName())
				.authorizationGrantType(authorizationGrantType)
				// 0.4.0 新增的方法
				.authorizedScopes(authorizedScopes);

			// ----- Access token -----
			OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
			OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
			if (generatedAccessToken == null) {
				OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
						"The token generator failed to generate the access token.", ERROR_URI);
				throw new OAuth2AuthenticationException(error);
			}
			OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
					generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
					generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
			if (generatedAccessToken instanceof ClaimAccessor) {
				authorizationBuilder.id(accessToken.getTokenValue())
					.token(accessToken,
							(metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
									((ClaimAccessor) generatedAccessToken).getClaims()))
					// 0.4.0 新增的方法
					.authorizedScopes(authorizedScopes)
					.attribute(Principal.class.getName(), authenticationToken);
			}
			else {
				authorizationBuilder.id(accessToken.getTokenValue()).accessToken(accessToken);
			}

			// ----- Refresh token -----
			OAuth2RefreshToken refreshToken = null;
			if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
			// Do not issue refresh token to public client
					!clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

				if (this.refreshTokenGenerator != null) {
					Instant issuedAt = Instant.now();
					Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getRefreshTokenTimeToLive());
					refreshToken = new OAuth2RefreshToken(this.refreshTokenGenerator.get(), issuedAt, expiresAt);
				}
				else {
					tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
					OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
					if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
						OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
								"The token generator failed to generate the refresh token.", ERROR_URI);
						throw new OAuth2AuthenticationException(error);
					}
					refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
				}
				authorizationBuilder.refreshToken(refreshToken);
			}

			OAuth2Authorization authorization = authorizationBuilder.build();

			this.authorizationService.save(authorization);

			log.debug("returning OAuth2AccessTokenAuthenticationToken");

			return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken,
					refreshToken, Objects.requireNonNull(authorization.getAccessToken().getClaims()));

		}
		catch (Exception ex) {
			log.error("problem in authenticate", ex);
			throw oAuth2AuthenticationException(authentication, (AuthenticationException) ex);
		}

	}

	/**
	 * 登录异常转换为oauth2异常
	 * @param authentication 身份验证
	 * @param authenticationException 身份验证异常
	 * @return {@link OAuth2AuthenticationException}
	 */
	private OAuth2AuthenticationException oAuth2AuthenticationException(Authentication authentication,
			AuthenticationException authenticationException) {
		if (authenticationException instanceof UsernameNotFoundException) {
			return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USERNAME_NOT_FOUND,
					I18nUtils.getLocale("JdbcDaoImpl.notFound", new Object[] { authentication.getName() },
							"Username {0} not found"),
					""));
		}
		if (authenticationException instanceof BadCredentialsException) {
			return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.BAD_CREDENTIALS,
					I18nUtils.getLocale("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
					""));
		}
		if (authenticationException instanceof LockedException) {
			return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_LOCKED,
					I18nUtils.getLocale("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"),
					""));
		}
		if (authenticationException instanceof DisabledException) {
			return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_DISABLE,
					I18nUtils.getLocale("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"), ""));
		}
		if (authenticationException instanceof AccountExpiredException) {
			return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_EXPIRED, I18nUtils
				.getLocale("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"), ""));
		}
		if (authenticationException instanceof CredentialsExpiredException) {
			return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.CREDENTIALS_EXPIRED,
					I18nUtils.getLocale("AbstractUserDetailsAuthenticationProvider.credentialsExpired",
							"User credentials have expired"),
					""));
		}
		if (authenticationException instanceof ScopeException) {
			return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_SCOPE,
					I18nUtils.getLocale("AbstractAccessDecisionManager.accessDenied", "invalid_scope"), ""));
		}
		if (authenticationException instanceof SmsCodeException) {
			String message = authenticationException.getMessage();

			return new OAuth2AuthenticationException(
					new OAuth2Error(Oauth2ErrorConstants.SMS_CODE_ERROR, authenticationException.getMessage(), ""));
		}
		if (authenticationException instanceof UserFrozenException) {
			return new OAuth2AuthenticationException(
					new OAuth2Error(Oauth2ErrorConstants.USER_FREEZE, authenticationException.getMessage(), ""));
		}
		return new OAuth2AuthenticationException(OAuth2ErrorCodesExpand.UN_KNOW_LOGIN_ERROR);
	}

	private OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(
			Authentication authentication) {

		OAuth2ClientAuthenticationToken clientPrincipal = null;

		if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
			clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
		}

		if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
			return clientPrincipal;
		}

		throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
	}

}
