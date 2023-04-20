package com.twelvet.auth.support.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 自定义授权模式抽象
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationToken extends AbstractAuthenticationToken {

	private AuthorizationGrantType authorizationGrantType;

	private Authentication clientPrincipal;

	private Set<String> scopes;

	private Map<String, Object> additionalParameters;

	public AuthorizationGrantType getAuthorizationGrantType() {
		return authorizationGrantType;
	}

	public Authentication getClientPrincipal() {
		return clientPrincipal;
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public Map<String, Object> getAdditionalParameters() {
		return additionalParameters;
	}

	public OAuth2ResourceOwnerBaseAuthenticationToken(AuthorizationGrantType authorizationGrantType,
			Authentication clientPrincipal, @Nullable Set<String> scopes,
			@Nullable Map<String, Object> additionalParameters) {
		super(Collections.emptyList());
		Assert.notNull(authorizationGrantType, "authorizationGrantType cannot be null");
		Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
		this.authorizationGrantType = authorizationGrantType;
		this.clientPrincipal = clientPrincipal;
		this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
		this.additionalParameters = Collections.unmodifiableMap(
				additionalParameters != null ? new HashMap<>(additionalParameters) : Collections.emptyMap());
	}

	/**
	 * 扩展模式一般不需要密码
	 */
	@Override
	public Object getCredentials() {
		return "";
	}

	/**
	 * 获取用户名
	 */
	@Override
	public Object getPrincipal() {
		return this.clientPrincipal;
	}

}
