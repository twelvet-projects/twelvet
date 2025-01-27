package com.twelvet.auth.service;

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
	 * @param oauthCode 需要获取登录的第三方
	 * @return 返回登录地址
	 */
	String getAuthorize(String oauthCode);

}
