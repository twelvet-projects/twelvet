package com.twelvet.security.support.handler;

import cn.hutool.core.collection.CollUtil;
import com.twelvet.framework.log.utils.SysLogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @date 2022-06-02
 *
 * 事件机制处理退出相关
 */
@Component
public class TWTLogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

	private static final Logger log = LoggerFactory.getLogger(TWTLogoutSuccessEventHandler.class);

	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		if (CollUtil.isNotEmpty(authentication.getAuthorities())) {
			handle(authentication);
		}
	}

	/**
	 * 处理退出成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 */
	public void handle(Authentication authentication) {
		log.info("用户：{} 退出成功", authentication.getPrincipal());
	}

}
