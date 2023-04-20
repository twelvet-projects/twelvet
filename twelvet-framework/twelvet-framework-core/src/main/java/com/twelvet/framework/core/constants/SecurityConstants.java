package com.twelvet.framework.core.constants;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 权限相关常量
 */
public interface SecurityConstants {

	/**
	 * 登录成功
	 */
	String LOGIN_SUCCESS = "0";

	/**
	 * 退出成功
	 */
	String LOGOUT_SUCCESS = "2";

	/**
	 * 登录失败
	 */
	String LOGIN_FAIL = "1";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";

	/**
	 * {noop} 加密的特征码
	 */
	String NOOP = "{noop}";

	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth2/token";

	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

	/**
	 * 授权码模式confirm
	 */
	String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";

	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";

	/**
	 * 项目的license
	 */
	String PROJECT_LICENSE = "https://twelvet.cn/docs/";

	/**
	 * 内部请求标志
	 */
	String REQUEST_SOURCE = "request-source";

	/**
	 * 内部请求
	 */
	String INNER = "inner";

	/**
	 * 请求header
	 */
	String HEADER_FROM_IN = REQUEST_SOURCE + "=" + INNER;

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";

	/**
	 * 客户端ID
	 */
	String CLIENT_ID = "clientId";

	/**
	 * 用户信息
	 */
	String DETAILS_USER = "user_info";

	/**
	 * 手机号登录
	 */
	String SMS = "sms";

	/**
	 * 短信登录 参数名称
	 */
	String SMS_PARAMETER_NAME = "mobile";

	/**
	 * 验证码
	 */
	String CODE = "code";

}
