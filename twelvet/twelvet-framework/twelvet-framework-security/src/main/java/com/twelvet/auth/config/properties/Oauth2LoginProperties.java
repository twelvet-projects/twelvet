package com.twelvet.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 第三方OAuth2登录配置
 * <p>
 *
 * @since 2025/1/22
 */
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2LoginProperties {

	/**
	 * GitHub第三方登录配置
	 */
	private GitHubProperties github;

	public GitHubProperties getGithub() {
		return github;
	}

	public void setGithub(GitHubProperties github) {
		this.github = github;
	}

	/**
	 * GitHub第三方登录配置
	 */
	public static class GitHubProperties {

		/**
		 * clientId
		 */
		private String clientId;

		/**
		 * clientSecret
		 */
		private String clientSecret;

		/**
		 * 登录成功后的回调地址
		 */
		private String redirectUri;

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}

		public String getRedirectUri() {
			return redirectUri;
		}

		public void setRedirectUri(String redirectUri) {
			this.redirectUri = redirectUri;
		}

	}

}
