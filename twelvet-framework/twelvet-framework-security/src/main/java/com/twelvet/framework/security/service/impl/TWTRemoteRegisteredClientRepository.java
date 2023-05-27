package com.twelvet.framework.security.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.api.system.feign.RemoteOauth2ClientDetailsService;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.core.domain.utils.ResUtils;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 实现储存redis个性化
 */
public class TWTRemoteRegisteredClientRepository implements RegisteredClientRepository {

	/**
	 * 刷新令牌有效期默认 30 填
	 */
	private final static int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;

	/**
	 * 请求令牌有效期默认 12 小时
	 */
	private final static int accessTokenValiditySeconds = 60 * 60 * 12;

	@Autowired
	private RemoteOauth2ClientDetailsService remoteOauth2ClientDetailsService;

	/**
	 * Saves the registered client.
	 *
	 * <p>
	 * IMPORTANT: Sensitive information should be encoded externally from the
	 * implementation, e.g. {@link RegisteredClient#getClientSecret()}
	 * @param registeredClient the {@link RegisteredClient}
	 */
	@Override
	public void save(RegisteredClient registeredClient) {
		// throw new UnsupportedOperationException();
	}

	/**
	 * Returns the registered client identified by the provided {@code id}, or
	 * {@code null} if not found.
	 * @param id the registration identifier
	 * @return the {@link RegisteredClient} if found, otherwise {@code null}
	 */
	@Override
	public RegisteredClient findById(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the registered client identified by the provided {@code clientId}, or
	 * {@code null} if not found.
	 * @param clientId the client identifier
	 * @return the {@link RegisteredClient} if found, otherwise {@code null}
	 */

	/**
	 * 重写原生方法支持redis缓存
	 * @param clientId
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public RegisteredClient findByClientId(String clientId) {
		SysClientDetails clientDetails = ResUtils.of(remoteOauth2ClientDetailsService.getClientDetailsById(clientId))
			.getData()
			.orElseThrow(() -> new OAuth2AuthorizationCodeRequestAuthenticationException(
					new OAuth2Error("客户端查询异常，请检查数据库链接"), null));

		RegisteredClient.Builder builder = RegisteredClient.withId(clientDetails.getClientId())
			.clientId(clientDetails.getClientId())
			.clientSecret(SecurityConstants.NOOP + clientDetails.getClientSecret())
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

		// 授权模式
		Optional.ofNullable(clientDetails.getAuthorizedGrantTypes())
			.ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
				.forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));
		// 回调地址
		Optional.ofNullable(clientDetails.getWebServerRedirectUri()).ifPresent(builder::redirectUri);

		// scope
		Optional.ofNullable(clientDetails.getScope()).ifPresent(builder::scope);
		return builder
			.tokenSettings(TokenSettings.builder()
				.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
				.accessTokenTimeToLive(Duration.ofSeconds(
						Optional.ofNullable(clientDetails.getAccessTokenValidity()).orElse(accessTokenValiditySeconds)))
				.refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetails.getRefreshTokenValidity())
					.orElse(refreshTokenValiditySeconds)))
				.build())
			.clientSettings(ClientSettings.builder()
				.requireAuthorizationConsent(!BooleanUtil.toBoolean(clientDetails.getAutoapprove()))
				.build())
			.build();

	}

}
