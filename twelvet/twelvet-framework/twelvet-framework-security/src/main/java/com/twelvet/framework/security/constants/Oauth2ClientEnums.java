package com.twelvet.framework.security.constants;

import java.util.Arrays;

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

	/**
	 * 通过clientId获取枚举信息
	 * @param clientId clientId
	 * @return Oauth2ClientEnums
	 */
	public static Oauth2ClientEnums getByClientId(String clientId) {
		return Arrays.stream(Oauth2ClientEnums.values())
			.filter(oauth2ClientEnums -> oauth2ClientEnums.getClientId().equals(clientId))
			.findFirst()
			.orElse(null);
	}

}