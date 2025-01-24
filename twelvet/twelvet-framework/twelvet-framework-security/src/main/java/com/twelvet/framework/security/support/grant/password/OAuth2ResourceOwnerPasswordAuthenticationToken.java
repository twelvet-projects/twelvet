package com.twelvet.framework.security.support.grant.password;

import com.twelvet.framework.security.support.base.OAuth2ResourceOwnerBaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 密码授权token信息
 */
public class OAuth2ResourceOwnerPasswordAuthenticationToken extends OAuth2ResourceOwnerBaseAuthenticationToken {

	private final Object principal;

	private Object credentials;

	public OAuth2ResourceOwnerPasswordAuthenticationToken(AuthorizationGrantType authorizationGrantType,
			Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
		super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
		this.principal = clientPrincipal;
	}

	public OAuth2ResourceOwnerPasswordAuthenticationToken(UserDetails sysUser) {
		super(sysUser.getAuthorities());
		this.principal = sysUser;
		super.setAuthenticated(true); // 设置认证成功 必须
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
