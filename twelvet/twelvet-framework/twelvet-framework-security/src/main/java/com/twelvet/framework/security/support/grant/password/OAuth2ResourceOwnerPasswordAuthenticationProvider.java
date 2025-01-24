package com.twelvet.framework.security.support.grant.password;

import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import com.twelvet.framework.security.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import com.twelvet.framework.security.support.service.TwUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Objects;
import java.util.Optional;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 处理用户名密码授权
 */
public class OAuth2ResourceOwnerPasswordAuthenticationProvider
		extends OAuth2ResourceOwnerBaseAuthenticationProvider<OAuth2ResourceOwnerPasswordAuthenticationToken> {

	private static final Logger log = LogManager.getLogger(OAuth2ResourceOwnerPasswordAuthenticationProvider.class);

	private final PasswordEncoder passwordEncoder;

	private final MessageSourceAccessor messages;

	/**
	 * Constructs an {@code OAuth2AuthorizationCodeAuthenticationProvider} using the
	 * provided parameters.
	 * @param authenticationManager
	 * @param authorizationService the authorization service
	 * @param tokenGenerator the token generator
	 * @since 0.2.3
	 */
	public OAuth2ResourceOwnerPasswordAuthenticationProvider(AuthenticationManager authenticationManager,
			OAuth2AuthorizationService authorizationService,
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
		super(authenticationManager, authorizationService, tokenGenerator);
		this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		this.messages = SpringSecurityMessageSource.getAccessor();
	}

	/**
	 * 获取已获得授权的AuthenticationToken
	 * @param authentication 构建参数
	 * @return Authentication
	 */
	@Override
	public Authentication buildAuthenticationToken(Authentication authentication) {
		OAuth2ResourceOwnerPasswordAuthenticationToken oAuth2ResourceOwnerPasswordAuthenticationToken = (OAuth2ResourceOwnerPasswordAuthenticationToken) authentication;
		Map<String, Object> additionalParameters = oAuth2ResourceOwnerPasswordAuthenticationToken
			.getAdditionalParameters();
		String username = (String) additionalParameters.get(OAuth2ParameterNames.USERNAME);
		String password = (String) additionalParameters.get(OAuth2ParameterNames.PASSWORD);

		Map<String, TwUserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(TwUserDetailsService.class);

		String clientId = oAuth2ResourceOwnerPasswordAuthenticationToken.getClientPrincipal().getName();
		String grantType = oAuth2ResourceOwnerPasswordAuthenticationToken.getAuthorizationGrantType().getValue();

		// 获取需要使用的登录器
		Optional<TwUserDetailsService> optional = userDetailsServiceMap.values()
			.stream()
			.filter(service -> service.support(clientId, grantType))
			.max(Comparator.comparingInt(Ordered::getOrder));

		if (optional.isEmpty()) {
			throw new InternalAuthenticationServiceException("UserDetailsService error , not register");
		}

		try {
			UserDetails userDetails = optional.get().loadUserByUsername(username);
			if (Objects.isNull(userDetails)) {
				log.debug("Failed to authenticate since no credentials provided");
				throw new BadCredentialsException(I18nUtils
					.getLocale("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}

			// 检验密码是否正确
			if (!this.passwordEncoder.matches(password, userDetails.getPassword())) {
				log.debug("Failed to authenticate since password does not match stored value");
				throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}

			return new OAuth2ResourceOwnerPasswordAuthenticationToken(userDetails);
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
		boolean supports = OAuth2ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authentication);
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
