package com.twelvet.auth.support.core;

import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.security.domain.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: token 输出增强
 */
public class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

	/**
	 * Customize the OAuth 2.0 Token attributes.
	 * @param context the context containing the OAuth 2.0 Token attributes
	 */
	@Override
	public void customize(OAuth2TokenClaimsContext context) {
		OAuth2TokenClaimsSet.Builder claims = context.getClaims();
		claims.claim(SecurityConstants.DETAILS_LICENSE, SecurityConstants.PROJECT_LICENSE);
		String clientId = context.getAuthorizationGrant().getName();
		claims.claim(SecurityConstants.CLIENT_ID, clientId);
		// 客户端模式不返回具体用户信息
		if (SecurityConstants.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
			return;
		}

		LoginUser loginUser = (LoginUser) context.getPrincipal().getPrincipal();
		claims.claim(SecurityConstants.DETAILS_USER, loginUser);
		claims.claim("code", HttpStatus.OK.value());
	}

}
