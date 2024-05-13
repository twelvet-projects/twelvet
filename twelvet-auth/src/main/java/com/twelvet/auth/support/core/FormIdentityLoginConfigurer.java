package com.twelvet.auth.support.core;

import com.twelvet.auth.config.CustomOAuth2UserService;
import com.twelvet.auth.support.handler.FormAuthenticationFailureHandler;
import com.twelvet.auth.support.handler.SsoLogoutSuccessHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 基于授权码模式 统一认证登录 spring security & sas 都可以使用 所以抽取成 HttpConfigurer
 */
public final class FormIdentityLoginConfigurer
		extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

	@Override
	public void init(HttpSecurity http) throws Exception {
		// 必须密码登录顺序第一
		http.formLogin(formLogin -> {
			formLogin.loginPage("/token/login");
			formLogin.loginProcessingUrl("/token/form");
			formLogin.failureHandler(new FormAuthenticationFailureHandler());

		}).logout(logout -> {
			// SSO登出成功处理
			logout.logoutSuccessHandler(new SsoLogoutSuccessHandler()).deleteCookies("JSESSIONID");
			logout.invalidateHttpSession(true);
		}).csrf(AbstractHttpConfigurer::disable);

		// 开启第三方登录（GitHub）注意顺序，否则则会强制执行第三方登录优先
		Map<String, OAuth2UserService<OAuth2UserRequest, OAuth2User>> userServiceMap = new HashMap<>();
		http.oauth2Login(httpSecurityOAuth2LoginConfigurer ->
						httpSecurityOAuth2LoginConfigurer
								//.successHandler(new TWTAuthenticationSuccessEventHandler())
								.userInfoEndpoint(userInfo -> userInfo
										// 自定义授权，默认支持大部分OAuth2流程
										.userService(new CustomOAuth2UserService<>(userServiceMap)))
								// 需要提供能够呈现自定义登录页面的@Controller。@RequestMapping("/login/oauth2")
								//.loginPage("/login/oauth2")
								.authorizationEndpoint(authorization -> authorization
										// 默认发起请求地址：/oauth2/authorization/*
										.baseUri("/oauth2/authorization"))
								// 默认重定向：/login/oauth2/code/*
								.redirectionEndpoint(redirection -> redirection
										.baseUri("/login/oauth2/code/*"))
				);
				// Accept access tokens for User Info and/or Client Registration
				//.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
	}

}
