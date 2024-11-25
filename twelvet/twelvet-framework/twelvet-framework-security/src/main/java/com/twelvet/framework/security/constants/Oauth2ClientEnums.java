package com.twelvet.framework.security.constants;

/**
 * <p>
 * OAuth2客户端
 * <p>
 *
 * @since 2024/8/8
 */
public enum Oauth2ClientEnums {

	TWELVET("twelvet", "twelvet"),

	;

	/**
	 * 客户端client
	 */
	private final String clientId;

	/**
	 * 描述
	 */
	private final String description;

	Oauth2ClientEnums(String clientId, String description) {
		this.clientId = clientId;
		this.description = description;
	}

	public String getClientId() {
		return clientId;
	}

	public String getDescription() {
		return description;
	}

}