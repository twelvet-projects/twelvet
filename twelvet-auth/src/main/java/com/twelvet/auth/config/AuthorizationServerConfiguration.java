package com.twelvet.auth.config;

import com.twelvet.auth.support.core.FormIdentityLoginConfigurer;
import com.twelvet.auth.support.handler.TWTAuthenticationFailureEventHandler;
import com.twelvet.auth.support.handler.TWTAuthenticationSuccessEventHandler;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.security.support.CustomeOAuth2AccessTokenGenerator;
import com.twelvet.framework.security.support.core.CustomOAuth2TokenCustomizer;
import com.twelvet.framework.security.support.grant.oauth2.github.OAuth2ResourceOwnerGiHubAuthenticationProvider;
import com.twelvet.framework.security.support.grant.oauth2.github.OAuth2ResourceOwnerGitHubAuthenticationConverter;
import com.twelvet.framework.security.support.grant.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.twelvet.framework.security.support.grant.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.twelvet.framework.security.support.grant.sms.OAuth2ResourceOwnerSmsAuthenticationConverter;
import com.twelvet.framework.security.support.grant.sms.OAuth2ResourceOwnerSmsAuthenticationProvider;
import me.zhyd.oauth.request.AuthGithubRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeRequestAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

import java.util.Arrays;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 授权配置
 */
@Configuration
public class AuthorizationServerConfiguration {

	@Autowired
	private OAuth2AuthorizationService authorizationService;

	@Autowired
	private AuthGithubRequest authGithubRequest;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

		// 个性化认证授权端点
		http.with(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {
			// 注入自定义的授权认证Converter
			tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
				// 登录成功处理器
				.accessTokenResponseHandler(new TWTAuthenticationSuccessEventHandler())
				// 登录失败处理器
				.errorResponseHandler(new TWTAuthenticationFailureEventHandler());
		})
			// Enable OpenID Connect 1.0
			// .oidc(Customizer.withDefaults())
			// 个性化客户端认证
			.clientAuthentication(oAuth2ClientAuthenticationConfigurer -> {
				oAuth2ClientAuthenticationConfigurer
					// 处理客户端认证异常
					.errorResponseHandler(new TWTAuthenticationFailureEventHandler());
			})
			.authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
				// 授权码端点个性化confirm页面
				.consentPage(SecurityConstants.CUSTOM_CONSENT_PAGE_URI)), Customizer.withDefaults());

		// 授权码登录的登录页个性化
		http.with(new FormIdentityLoginConfigurer(), Customizer.withDefaults());

		http.with(authorizationServerConfigurer.authorizationService(authorizationService)// redis存储token的实现
			.authorizationServerSettings(
					AuthorizationServerSettings.builder().issuer(SecurityConstants.PROJECT_LICENSE).build()),
				Customizer.withDefaults());

		DefaultSecurityFilterChain securityFilterChain = http.authorizeHttpRequests(authorizeRequests -> {
			// 自定义接口、端点暴露
			authorizeRequests.requestMatchers(
					// Swagger
					"/v3/api-docs",
					// 监控
					"/actuator/**",
					// 资源
					"/assets/**",
					// 错误信息
					"/error",
					// OAuth2
					"/token/**",
					// 第三方授权OAuth2
					"/login/oauth2/**",
					// api相关，token管理
					"/api/token/*")
				.permitAll();
			authorizeRequests.anyRequest().authenticated();
		}).build();

		// 注入自定义授权模式实现
		addCustomOAuth2GrantAuthenticationProvider(http);
		return securityFilterChain;
	}

	/**
	 * 令牌生成规则实现 </br>
	 * client:username:uuid
	 * @return OAuth2TokenGenerator
	 */
	@Bean
	public OAuth2TokenGenerator oAuth2TokenGenerator() {
		CustomeOAuth2AccessTokenGenerator accessTokenGenerator = new CustomeOAuth2AccessTokenGenerator();
		// 注入Token 增加关联用户信息
		accessTokenGenerator.setAccessTokenCustomizer(new CustomOAuth2TokenCustomizer());
		return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
	}

	/**
	 * request -> xToken 注入请求转换器
	 * @return DelegatingAuthenticationConverter
	 */
	private AuthenticationConverter accessTokenRequestConverter() {
		return new DelegatingAuthenticationConverter(Arrays.asList(
				// 密码模式
				new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
				// 手机模式
				new OAuth2ResourceOwnerSmsAuthenticationConverter(),
				// 第三方GitHub登录
				new OAuth2ResourceOwnerGitHubAuthenticationConverter(),

				// 刷新模式
				new OAuth2RefreshTokenAuthenticationConverter(),
				// 授权码模式
				new OAuth2AuthorizationCodeAuthenticationConverter(),
				// 客户端模式
				new OAuth2ClientCredentialsAuthenticationConverter(),
				new OAuth2AuthorizationCodeRequestAuthenticationConverter())

		);
	}

	/**
	 * 注入授权模式实现提供方
	 * <p>
	 * 1. 密码模式 </br>
	 * 2. 短信登录 </br>
	 */
	@SuppressWarnings("unchecked")
	private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

		OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider = new OAuth2ResourceOwnerPasswordAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

		OAuth2ResourceOwnerSmsAuthenticationProvider resourceOwnerSmsAuthenticationProvider = new OAuth2ResourceOwnerSmsAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

		OAuth2ResourceOwnerGiHubAuthenticationProvider resourceOwnerGitHubAuthenticationProvider = new OAuth2ResourceOwnerGiHubAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator(), authGithubRequest);

		// 处理 OAuth2ResourceOwnerPasswordAuthenticationToken
		http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);
		// 处理 OAuth2ResourceOwnerSmsAuthenticationToken
		http.authenticationProvider(resourceOwnerSmsAuthenticationProvider);
		// 处理 第三方GitHub登录
		http.authenticationProvider(resourceOwnerGitHubAuthenticationProvider);
	}

}
