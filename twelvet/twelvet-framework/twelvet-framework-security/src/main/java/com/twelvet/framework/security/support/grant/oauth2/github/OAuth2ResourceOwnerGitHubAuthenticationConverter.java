package com.twelvet.framework.security.support.grant.oauth2.github;

import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import com.twelvet.framework.security.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import com.twelvet.framework.security.support.grant.oauth2.github.constant.SecurityOauth2Constants;
import com.twelvet.framework.security.utils.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 密码认证转换器
 */
public class OAuth2ResourceOwnerGitHubAuthenticationConverter
		extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerGitHubAuthenticationToken> {

	@Override
	public boolean support(String grantType) {
		return Oauth2GrantEnums.GITHUB.getGrant().equals(grantType);
	}

	@Override
	public OAuth2ResourceOwnerGitHubAuthenticationToken buildToken(Authentication clientPrincipal,
			Set<String> requestedScopes, Map<String, Object> additionalParameters) {
		return new OAuth2ResourceOwnerGitHubAuthenticationToken(
				new AuthorizationGrantType(Oauth2GrantEnums.GITHUB.getGrant()), clientPrincipal, requestedScopes,
				additionalParameters);
	}

	/**
	 * 校验扩展参数 密码模式密码必须不为空
	 * @param request 参数列表
	 */
	@Override
	public void checkParams(HttpServletRequest request) {
		MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
		// code (REQUIRED)
		String code = parameters.getFirst(SecurityOauth2Constants.CODE);
		if (!StringUtils.hasText(code) || parameters.get(SecurityOauth2Constants.CODE).size() != 1) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, SecurityOauth2Constants.CODE,
					OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		// state (REQUIRED)
		String state = parameters.getFirst(SecurityOauth2Constants.STATE);
		if (!StringUtils.hasText(state) || parameters.get(SecurityOauth2Constants.STATE).size() != 1) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, SecurityOauth2Constants.STATE,
					OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
	}

}
