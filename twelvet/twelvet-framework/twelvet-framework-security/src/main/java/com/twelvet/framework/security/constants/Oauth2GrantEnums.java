package com.twelvet.framework.security.constants;

/**
 * OAuth2 登录支持的类型
 */
public enum Oauth2GrantEnums {

	PASSWORD("password", "账号密码登录"), SMS("sms", "短信登录"), GITHUB("github", "GitHub第三方登录"),

	;

	/**
	 * 客户端grant
	 */
	private final String grant;

	/**
	 * 描述
	 */
	private final String description;

	Oauth2GrantEnums(String grant, String description) {
		this.grant = grant;
		this.description = description;
	}

	public String getGrant() {
		return grant;
	}

	public String getDescription() {
		return description;
	}

}
