package com.twelvet.auth.config;

import com.twelvet.auth.config.properties.Oauth2LoginProperties;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGithubRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public AuthGithubRequest authGithubRequest(Oauth2LoginProperties oauth2LoginProperties) {
		return new AuthGithubRequest(AuthConfig.builder()
			.clientId(oauth2LoginProperties.getGithub().getClientId())
			.clientSecret(oauth2LoginProperties.getGithub().getClientSecret())
			.redirectUri(oauth2LoginProperties.getGithub().getRedirectUri())
			.build());
	}

}