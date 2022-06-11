package com.twelvet.security.config;

import com.twelvet.security.support.core.FormIdentityLoginConfigurer;
import com.twelvet.security.support.core.TWTDaoAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration {

	/**
	 * spring security 默认的安全策略
	 * @param http security注入点
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers("/token/*").permitAll()// 开放自定义的部分端点
				.anyRequest().authenticated()).headers().frameOptions().sameOrigin()// 避免iframe同源无法登录
				.and().apply(new FormIdentityLoginConfigurer()); // 表单登录个性化
		// 处理 UsernamePasswordAuthenticationToken
		http.authenticationProvider(new TWTDaoAuthenticationProvider());
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/actuator/**", "/css/**", "/error");
	}

}