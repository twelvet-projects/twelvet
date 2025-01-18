package com.twelvet.auth.service.impl;

import com.twelvet.auth.service.Oauth2AuthService;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *
 * <p>
 *
 * @since 2025/1/16
 */
@Service
public class Oauth2AuthServiceImpl implements Oauth2AuthService {

	/**
	 * 第三方授权地址
	 * @return 第三方授权地址
	 */
	public String getAuthorize() {
		AuthRequest authRequest = getAuthRequest();
		return authRequest.authorize(AuthStateUtils.createState());
	}

	public Object login(AuthCallback callback) {
		AuthRequest authRequest = getAuthRequest();
		return authRequest.login(callback);
	}

	private AuthRequest getAuthRequest() {
		return new AuthGithubRequest(AuthConfig.builder()
			.clientId("ff")
			.clientSecret("a82624")
			.redirectUri("http://localhost:8080/auth/login/oauth2/code/github")
			.build());
	}

}
