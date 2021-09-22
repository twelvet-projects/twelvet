package com.twelvet.security.granter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 认证中心启动器
 * @EnableFeignClients 自定义授权模式
 */
public abstract class AbstractCustomTokenGranter extends AbstractTokenGranter {

    protected AbstractCustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }


    /**
     * 重写
     *
     * @param client       ClientDetails
     * @param tokenRequest TokenRequest
     * @return OAuth2Authentication
     */
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = tokenRequest.getRequestParameters();
        UserDetails customUser = getUserDetails(parameters);
        PreAuthenticatedAuthenticationToken authentication = null;
        if (customUser == null) {
            throw new InvalidGrantException("无法获取用户信息");
        }
        OAuth2Request oAuth2Request = null;
        try {

            oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
            authentication = new PreAuthenticatedAuthenticationToken(customUser, null, customUser.getAuthorities());
            authentication.setDetails(customUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new OAuth2Authentication(oAuth2Request, authentication);
    }

    /**
     * 获取用户信息
     *
     * @param parameters Map<String, String>
     * @return UserDetails
     */
    protected abstract UserDetails getUserDetails(Map<String, String> parameters);
}