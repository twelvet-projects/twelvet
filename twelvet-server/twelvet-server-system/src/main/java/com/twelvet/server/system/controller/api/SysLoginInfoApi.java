package com.twelvet.server.system.controller.api;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.jdbc.web.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.utils.http.IpUtils;
import com.twelvet.server.system.service.ISysLoginInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统操作/访问日志
 */
@Api(tags = "系统操作/访问日志API")
@RestController
@RequestMapping("/api/loginInfo")
public class SysLoginInfoApi extends TWTController {

    @Autowired
    private ISysLoginInfoService iSysLoginInfoService;

    /**
     * 记录登录信息
     *
     * @param username 账号
     * @param status   是否登录成功
     * @param message  登录系统信息
     * @return 记录结果
     */
    @AuthIgnore
    @PostMapping
    public void add(
            @RequestParam("username") String username,
            @RequestParam("deptId") Long deptId,
            @RequestParam("status") Integer status,
            @RequestParam("message") String message
    ) {
        // 获取IP
        String ip = IpUtils.getIpAddr();


        SysLoginInfo loginInfo = new SysLoginInfo();
        loginInfo.setUserName(username);
        loginInfo.setIpaddr(ip);
        loginInfo.setDeptId(deptId);
        loginInfo.setStatus(status);
        loginInfo.setMsg(message);

        iSysLoginInfoService.insertLoginInfo(loginInfo);
    }

}
