package com.twelvet.auth.support.core;

import com.twelvet.auth.support.handler.FormAuthenticationFailureHandler;
import com.twelvet.auth.support.handler.SsoLogoutSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 基于授权码模式 统一认证登录 spring security & sas 都可以使用 所以抽取成 HttpConfigurer
 */
public final class FormIdentityLoginConfigurer
		extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

	@Override
	public void init(HttpSecurity http) throws Exception {
		http.formLogin(formLogin -> {
			formLogin.loginPage("/token/login");
			formLogin.loginProcessingUrl("/token/form");
			formLogin.failureHandler(new FormAuthenticationFailureHandler());

		}).logout(logout -> {
			// SSO登出成功处理
			logout.logoutSuccessHandler(new SsoLogoutSuccessHandler()).deleteCookies("JSESSIONID");
			logout.invalidateHttpSession(true);
		}).csrf(AbstractHttpConfigurer::disable);
	}

}
