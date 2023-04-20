package com.twelvet.framework.security.aspect;

import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.access.AccessDeniedException;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 忽略内部请求的授权
 */
@Aspect
public class TWTSecurityInnerAspect implements Ordered {

	private final static Logger log = LoggerFactory.getLogger(TWTSecurityInnerAspect.class);

	@Around("@annotation(authIgnore)")
	public Object around(ProceedingJoinPoint point, AuthIgnore authIgnore) throws Throwable {
		String header = ServletUtils.getRequest().get().getHeader(SecurityConstants.REQUEST_SOURCE);

		if (authIgnore.value() && !StringUtils.equals(SecurityConstants.INNER, header)) {
			log.warn("访问接口 {} 没有权限", point.getSignature().getName());
			throw new AccessDeniedException("Access is denied");
		}
		return point.proceed();
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}

}
