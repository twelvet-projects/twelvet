package com.twelvet.framework.security.support.grant.sms;

import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.security.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import com.twelvet.framework.security.support.grant.password.OAuth2ResourceOwnerPasswordAuthenticationToken;
import com.twelvet.framework.security.support.service.TwUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
 * @Description: 短信登录的核心处理
 */
public class OAuth2ResourceOwnerSmsAuthenticationProvider
		extends OAuth2ResourceOwnerBaseAuthenticationProvider<OAuth2ResourceOwnerSmsAuthenticationToken> {

	private static final Logger log = LoggerFactory.getLogger(OAuth2ResourceOwnerSmsAuthenticationProvider.class);

	/**
	 * Constructs an {@code OAuth2AuthorizationCodeAuthenticationProvider} using the
	 * provided parameters.
	 * @param authenticationManager AuthenticationManager
	 * @param authorizationService the authorization service
	 * @param tokenGenerator the token generator
	 * @since 0.2.3
	 */
	public OAuth2ResourceOwnerSmsAuthenticationProvider(AuthenticationManager authenticationManager,
			OAuth2AuthorizationService authorizationService,
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
		super(authenticationManager, authorizationService, tokenGenerator);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		boolean supports = OAuth2ResourceOwnerSmsAuthenticationToken.class.isAssignableFrom(authentication);
		log.debug("supports authentication=" + authentication + " returning " + supports);
		return supports;
	}

	@Override
	public void checkClient(RegisteredClient registeredClient) {
		assert registeredClient != null;
		if (!registeredClient.getAuthorizationGrantTypes()
			.contains(new AuthorizationGrantType(Oauth2GrantEnums.SMS.getGrant()))) {
			throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
		}
	}

	@Override
	public Authentication buildAuthenticationToken(Authentication authentication) {
		OAuth2ResourceOwnerPasswordAuthenticationToken oAuth2ResourceOwnerPasswordAuthenticationToken = (OAuth2ResourceOwnerPasswordAuthenticationToken) authentication;
		Map<String, Object> additionalParameters = oAuth2ResourceOwnerPasswordAuthenticationToken
			.getAdditionalParameters();
		String phone = (String) additionalParameters.get(SecurityConstants.SMS_PARAMETER_NAME);

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
			UserDetails userDetails = optional.get().loadUserByUsername(phone);
			if (Objects.isNull(userDetails)) {
				log.debug("Failed to authenticate since no credentials provided");
				throw new BadCredentialsException(I18nUtils
					.getLocale("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
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
