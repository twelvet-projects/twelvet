package com.twelvet.framework.security.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 用户已被冻结
 */
public class UserFrozenException extends AuthenticationException {

	public UserFrozenException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UserFrozenException(String msg) {
		super(msg);
	}

}
