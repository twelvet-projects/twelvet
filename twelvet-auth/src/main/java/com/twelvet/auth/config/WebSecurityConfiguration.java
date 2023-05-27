package com.twelvet.auth.config;

import com.twelvet.auth.support.core.FormIdentityLoginConfigurer;
import com.twelvet.auth.support.core.TWTDaoAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: WEB安全配置
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration {

	/**
	 * spring security 默认的安全策略
	 * @param http security注入点
	 * @return SecurityFilterChain
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// 增加自定义第三方换取信息方式
		Map<String, OAuth2UserService<OAuth2UserRequest, OAuth2User>> oAuth2UserServiceMap = new HashMap<>();

		http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers("/token/*", "/api/token/*")
			.permitAll()// 开放自定义的部分端点
			.anyRequest()
			.authenticated())
			.headers()
			.frameOptions()
			.sameOrigin()// 避免iframe同源无法登录
			// 表单登录个性化
			.and()
			.apply(new FormIdentityLoginConfigurer())
			// 接入第三方登录
			.and()
			.oauth2Login()
			// 自定义获取授权信息方式
			.userInfoEndpoint()
			.userService(new CustomOAuth2UserService<>(oAuth2UserServiceMap));
		// 处理 UsernamePasswordAuthenticationToken
		http.authenticationProvider(new TWTDaoAuthenticationProvider());
		return http.build();
	}

	/**
	 * 暴露静态资源
	 * <p>
	 * <a href="https://github.com/spring-projects/spring-security/issues/10938">...</a>
	 * @param http
	 */
	@Bean
	@Order(0)
	SecurityFilterChain resources(HttpSecurity http) throws Exception {
		http.requestMatchers((matchers) -> matchers.antMatchers("/actuator/**", "/assets/**", "/error", "/v3/api-docs"))
			.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
			.requestCache()
			.disable()
			.securityContext()
			.disable()
			.sessionManagement()
			.disable();
		return http.build();
	}

}
