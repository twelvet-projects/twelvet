package com.twelvet.visual.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 监控权限配置
 */
@EnableWebSecurity
public class WebSecurityConfigurer {

	private final String adminContextPath;

	public WebSecurityConfigurer(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	/**
	 * spring security 默认的安全策略
	 * @param http security注入点
	 * @return SecurityFilterChain
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");
		http.headers()
			.frameOptions()
			.disable()
			.and()
			.authorizeRequests()
			.antMatchers(adminContextPath + "/assets/**", adminContextPath + "/login",
					adminContextPath + "/instances/**", adminContextPath + "/actuator/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage(adminContextPath + "/login")
			.successHandler(successHandler)
			.and()
			.logout()
			.logoutUrl(adminContextPath + "/logout")
			.and()
			.httpBasic()
			.and()
			.csrf()
			.disable();
		return http.build();
	}

}
