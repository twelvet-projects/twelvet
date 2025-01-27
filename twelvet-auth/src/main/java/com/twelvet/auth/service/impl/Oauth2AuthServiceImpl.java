package com.twelvet.auth.service.impl;

import com.twelvet.auth.service.Oauth2AuthService;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import me.zhyd.oauth.request.AuthGithubRequest;
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
	 * 获取第三方授权地址
	 * @param oauthCode 需要获取登录的第三方
	 * @return 返回登录地址
	 */
	public String getAuthorize(String oauthCode) {
		if (Oauth2GrantEnums.GITHUB.getGrant().equals(oauthCode)) {
			return authGithubRequest.authorize(AuthStateUtils.createState());
		}
		throw new TWTException("不存在此第三方登录授权方式");
	}

}
