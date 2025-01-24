package com.twelvet.framework.security.support.grant.sms;

import com.twelvet.framework.security.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
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
 * @Description: 短信登录转换器
 */
public class OAuth2ResourceOwnerSmsAuthenticationConverter
		extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerSmsAuthenticationToken> {

	/**
	 * 是否支持此convert
	 * @param grantType 授权类型
	 * @return
	 */
	@Override
	public boolean support(String grantType) {
		return Oauth2GrantEnums.SMS.getGrant().equals(grantType);
	}

	@Override
	public OAuth2ResourceOwnerSmsAuthenticationToken buildToken(Authentication clientPrincipal,
			Set<String> requestedScopes, Map<String, Object> additionalParameters) {
		return new OAuth2ResourceOwnerSmsAuthenticationToken(
				new AuthorizationGrantType(Oauth2GrantEnums.SMS.getGrant()), clientPrincipal, requestedScopes,
				additionalParameters);
	}

	/**
	 * 校验扩展参数 密码模式密码必须不为空
	 * @param request 参数列表
	 */
	@Override
	public void checkParams(HttpServletRequest request) {
		MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
		// PHONE (REQUIRED)
		String phone = parameters.getFirst(SecurityConstants.SMS_PARAMETER_NAME);
		if (!StringUtils.hasText(phone) || parameters.get(SecurityConstants.SMS_PARAMETER_NAME).size() != 1) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, SecurityConstants.SMS_PARAMETER_NAME,
					OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
	}

}
