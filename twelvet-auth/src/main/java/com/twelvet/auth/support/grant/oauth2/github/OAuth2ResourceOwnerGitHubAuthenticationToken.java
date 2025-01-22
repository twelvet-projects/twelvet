package com.twelvet.auth.support.grant.oauth2.github;

import com.twelvet.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 密码授权token信息
 */
public class OAuth2ResourceOwnerGitHubAuthenticationToken extends AbstractAuthenticationToken {

	private Object principal;

	public OAuth2ResourceOwnerGitHubAuthenticationToken() {
		super(AuthorityUtils.NO_AUTHORITIES);
	}

	public OAuth2ResourceOwnerGitHubAuthenticationToken(UserDetails userDetails) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.principal = userDetails;
		// 设置认证成功 必须
		super.setAuthenticated(true);
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public Object getCredentials() {
		return "";
	}

}
