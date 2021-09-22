package com.twelvet.security.handler;

import com.twelvet.api.system.feign.RemoteLogService;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.utils.TWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 认证成功处理
 */
@Component
public class AuthenticationSuccessEventHandler implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private RemoteLogService remoteLogService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (TWTUtils.isNotEmpty(authentication.getAuthorities())
                && authentication.getPrincipal() instanceof LoginUser) {
            LoginUser user = (LoginUser) authentication.getPrincipal();

            String username = user.getUsername();
            Long deptId = user.getDeptId();

            // 记录用户登录日志
            remoteLogService.saveLoginInfo(username, deptId, 1, "登录成功");
        }
    }
}
