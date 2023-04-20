package com.twelvet.auth.config;

import com.twelvet.framework.utils.TUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 自定义获取第三方code换信息
 */
public class CustomOAuth2UserService<R extends OAuth2UserRequest, U extends OAuth2User>
		implements OAuth2UserService<R, U> {

	/**
	 * 默认的获取方式，适配大部分第三方
	 */
	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultOAuth2UserService = new DefaultOAuth2UserService();

	/**
	 * 自定义换取方式
	 */
	private final Map<String, OAuth2UserService<R, U>> userServiceMap;

	public CustomOAuth2UserService(Map<String, OAuth2UserService<R, U>> userServiceMap) {
		this.userServiceMap = Collections.unmodifiableMap(userServiceMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public U loadUser(R userRequest) throws OAuth2AuthenticationException {
		Assert.notNull(userRequest, "userRequest cannot be null");

		// 第三方ID(可以通过此ID获取自定义授权方式)
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		OAuth2UserService<R, U> oAuth2UserService = userServiceMap.get(registrationId);

		if (TUtils.isEmpty(oAuth2UserService)) {
			// 采用默认换取方式
			oAuth2UserService = (OAuth2UserService<R, U>) defaultOAuth2UserService;
		}

		return oAuth2UserService.loadUser(userRequest);
	}

}
