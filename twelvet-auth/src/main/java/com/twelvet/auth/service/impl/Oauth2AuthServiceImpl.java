package com.twelvet.auth.service.impl;

import com.twelvet.auth.service.Oauth2AuthService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * <p>
 *
 * @since 2025/1/16
 */
@Service
public class Oauth2AuthServiceImpl implements Oauth2AuthService {

	@Autowired
	private AuthGithubRequest authGithubRequest;

	/**
	 * 第三方授权地址
	 * @return 第三方授权地址
	 */
	public String getAuthorize() {
		return authGithubRequest.authorize(AuthStateUtils.createState());
	}

	public Object login(AuthCallback callback) {
		return authGithubRequest.login(callback);
	}

}
