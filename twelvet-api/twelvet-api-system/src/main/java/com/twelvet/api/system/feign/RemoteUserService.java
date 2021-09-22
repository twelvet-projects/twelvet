package com.twelvet.api.system.feign;

import com.twelvet.api.system.feign.factory.RemoteUserFallbackFactory;
import com.twelvet.api.system.model.UserInfo;
import com.twelvet.framework.core.constants.ServiceNameConstants;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 用户信息服务
 */
@FeignClient(
        contextId = "remoteUserService",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteUserFallbackFactory.class
)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名称
     * @return R<UserInfo>
     */
    @GetMapping("/api/user/info/{username}")
    R<UserInfo> getUserInfo(@PathVariable("username") String username);
}
