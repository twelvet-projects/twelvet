package com.twelvet.framework.security.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 用户已被冻结
 */
public class UserFrozenException extends AccessDeniedException {

	public UserFrozenException(String msg) {
		super(msg);
	}

	public UserFrozenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
