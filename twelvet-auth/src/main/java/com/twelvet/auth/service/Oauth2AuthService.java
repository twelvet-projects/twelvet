package com.twelvet.auth.service;

import me.zhyd.oauth.model.AuthCallback;

/**
 * <p>
 * 第三方登录
 * <p>
 *
 * @since 2025/1/16
 */
public interface Oauth2AuthService {

	/**
	 * 获取第三方授权地址
	 * @return String
	 */
	String getAuthorize();

	/**
	 * 测试回调
	 * @param callback
	 * @return
	 */
	Object login(AuthCallback callback);

}
