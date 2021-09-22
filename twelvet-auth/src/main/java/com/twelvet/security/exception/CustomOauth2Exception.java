package com.twelvet.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 自定义认证异常
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {
    private static final long serialVersionUID = 1L;

    public CustomOauth2Exception(String msg) {
        super(msg);
    }
}
