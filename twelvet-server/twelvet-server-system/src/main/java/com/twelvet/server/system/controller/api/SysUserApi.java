package com.twelvet.server.system.controller.api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.twelvet.api.system.domain.SysUser;
import com.twelvet.api.system.model.UserInfo;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.utils.$;
import com.twelvet.server.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 用户信息
 */
@ApiSupport(author = "TwelveT")
@Api(tags = "用户信息API")
@RestController
@RequestMapping("/api/user")
public class SysUserApi extends TWTController {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 获取当前用户信息(认证中心服务专用)
     *
     * @param username String
     * @return R<UserInfo>
     */
    @ApiOperationSupport(author = "twelvet")
    @ApiOperation(value = "获取当前用户信息(认证中心服务专用)")
    @AuthIgnore
    @GetMapping("/info/{username}")
    public R<UserInfo> info(@PathVariable("username") String username) {
        SysUser sysUser = iSysUserService.selectUserByUserName(username, false);
        if ($.isEmpty(sysUser)) {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = iSysPermissionService.getRolePermission(sysUser.getUserId());
        // 权限集合
        Set<String> permissions = iSysPermissionService.getMenuPermission(sysUser.getUserId());
        UserInfo sysUserVo = new UserInfo();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }

}
