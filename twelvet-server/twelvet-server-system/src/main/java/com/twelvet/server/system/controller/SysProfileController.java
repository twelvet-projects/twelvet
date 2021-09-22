package com.twelvet.server.system.controller;


import com.twelvet.api.dfs.domain.SysFile;
import com.twelvet.api.dfs.feign.RemoteFileService;
import com.twelvet.api.system.domain.SysUser;
import com.twelvet.api.system.domain.params.UserPassword;
import com.twelvet.api.system.domain.vo.UserInfoVo;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TWTUtils;
import com.twelvet.server.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: DFS控制器
 */
@RestController
@RequestMapping("/user/profile")
public class SysProfileController extends TWTController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 个人信息
     *
     * @return AjaxResult
     */
    @GetMapping
    public AjaxResult profile() {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username, true);

        UserInfoVo userInfoVo = new UserInfoVo();

        userInfoVo.setUser(user);
        userInfoVo.setPostGroup(userService.selectUserPostGroup(username));
        userInfoVo.setRoleGroup(userService.selectUserRoleGroup(username));

        return AjaxResult.success(userInfoVo);
    }

    /**
     * 修改当前用户信息
     *
     * @param user SysUser
     * @return 修改结果
     */
    @Log(service = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        user.setUserId(userId);
        if (userService.updateUserProfile(user) > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 修改用户头像
     *
     * @param file
     * @return 上传信息
     */
    @Log(service = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarFile") MultipartFile file) {

        try {
            R<SysFile> fileResult = remoteFileService.upload(file);

            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }

            String url = fileResult.getData().getUrl();

            LoginUser user = SecurityUtils.getLoginUser();

            if (userService.updateUserAvatar(user.getUsername(), url)) {
                AjaxResult ajax = AjaxResult.success("设置成功");
                ajax.put("imgUrl", url);
                return ajax;
            }
        } catch (Exception e) {
            return AjaxResult.error("发生未知错误");
        }
        return AjaxResult.error("上传失败");
    }

    /**
     * 重置密码
     *
     * @param userPassword 用户修改密码参数
     * @return 重置结果
     */
    @Log(service = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(@RequestBody UserPassword userPassword) {

        if (!userPassword.getNewPassword().equals(userPassword.getConfirmPassword())) {
            return AjaxResult.error("确认密码不一致");
        }

        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username, true);
        String password = user.getPassword();

        if (!SecurityUtils.matchesPassword(userPassword.getOldPassword(), password)) {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }

        if (SecurityUtils.matchesPassword(userPassword.getNewPassword(), password)) {
            return AjaxResult.error("新密码不能与旧密码相同");
        }

        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(userPassword.getNewPassword())) > 0) {
            return AjaxResult.success();
        }

        return AjaxResult.error("修改密码异常，请联系管理员");
    }

}