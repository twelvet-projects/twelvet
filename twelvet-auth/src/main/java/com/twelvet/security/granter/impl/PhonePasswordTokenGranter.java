package com.twelvet.security.granter.impl;

import com.twelvet.security.granter.AbstractCustomTokenGranter;
import com.twelvet.security.service.impl.TwTUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 手机号密码模式
 */
public class PhonePasswordTokenGranter extends AbstractCustomTokenGranter {

    private static final String GRANT_TYPE = "phone_password";

    @Autowired
    private final TwTUserDetailsServiceImpl userDetailsService;

    public PhonePasswordTokenGranter(
            AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory,
            TwTUserDetailsServiceImpl userDetailsService
    ) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
    }

    /**
     * 获取登录信息进行登录
     *
     * @param parameters Map<String, String>
     * @return UserDetails
     */
    @Override
    protected UserDetails getUserDetails(Map<String, String> parameters) {
        String phone = parameters.get("account");
        String password = parameters.get("password");
        return userDetailsService.loadUserByPhoneAndPassword(phone, password);
    }
}