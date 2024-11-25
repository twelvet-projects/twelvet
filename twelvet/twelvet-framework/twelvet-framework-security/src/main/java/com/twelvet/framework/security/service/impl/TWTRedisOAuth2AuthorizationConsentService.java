package com.twelvet.framework.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 实现储存redis个性化
 */
public class TWTRedisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private final static Long TIMEOUT = 10L;

	@Override
	public void save(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");

		redisTemplate.opsForValue()
			.set(buildKey(authorizationConsent), authorizationConsent, TIMEOUT, TimeUnit.MINUTES);

	}

	@Override
	public void remove(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		redisTemplate.delete(buildKey(authorizationConsent));
	}

	@Override
	public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
		Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		return (OAuth2AuthorizationConsent) redisTemplate.opsForValue()
			.get(buildKey(registeredClientId, principalName));
	}

	private static String buildKey(String registeredClientId, String principalName) {
		return "token:consent:" + registeredClientId + ":" + principalName;
	}

	private static String buildKey(OAuth2AuthorizationConsent authorizationConsent) {
		return buildKey(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
	}

}
