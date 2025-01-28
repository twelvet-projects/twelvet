package com.twelvet.auth.config;

import com.twelvet.auth.config.properties.Oauth2LoginProperties;
import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGithubRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 第三方登录配置
 *
 * @author twelvet
 * @WebSite twelvet.cn
 */
@Configuration
public class Oauth2LoginConfiguration {

	/**
	 * Github第三方登录
	 * @param oauth2LoginProperties Oauth2LoginProperties
	 * @return AuthGithubRequest
	 */
	@Bean
	@ConditionalOnMissingBean
	public AuthGithubRequest authGithubRequest(Oauth2LoginProperties oauth2LoginProperties,
			AuthStateCache authStateCache) {
		return new AuthGithubRequest(AuthConfig.builder()
			.clientId(oauth2LoginProperties.getGithub().getClientId())
			.clientSecret(oauth2LoginProperties.getGithub().getClientSecret())
			.redirectUri(oauth2LoginProperties.getGithub().getRedirectUri())
			.httpConfig(HttpConfig.builder()
				.timeout(15000)
				// .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1",
				// 7890)))
				.build())
			.build(), authStateCache);
	}

}