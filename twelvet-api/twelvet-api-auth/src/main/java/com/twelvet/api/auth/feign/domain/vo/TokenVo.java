package com.twelvet.api.auth.feign.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 令牌管理VO
 */
@Schema(description = "令牌管理DTO")
public class TokenVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;

	/**
	 * 客户端ID
	 */
	@Schema(description = "客户端ID")
	private String clientId;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * accessToken
	 */
	@Schema(description = "accessToken")
	private String accessToken;

	/**
	 * refreshToken
	 */
	@Schema(description = "refreshToken")
	private String refreshToken;

	/**
	 * 授权类型
	 */
	@Schema(description = "授权类型")
	private String tokenType;

	/**
	 * 授权范围
	 */
	@Schema(description = "授权范围")
	private String scope;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private String issuedAt;

	/**
	 * 过期时间
	 */
	@Schema(description = "过期时间")
	private String expiresAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(String issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}

	@Override
	public String toString() {
		return "TokenVo{" + "id='" + id + '\'' + ", userId=" + userId + ", clientId='" + clientId + '\''
				+ ", username='" + username + '\'' + ", accessToken='" + accessToken + '\'' + ", refreshToken='"
				+ refreshToken + '\'' + ", tokenType='" + tokenType + '\'' + ", scope='" + scope + '\'' + ", issuedAt='"
				+ issuedAt + '\'' + ", expiresAt='" + expiresAt + '\'' + '}';
	}

}
