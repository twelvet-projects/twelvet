package com.twelvet.security.service.impl;

import com.twelvet.api.system.feign.RemoteUserService;
import com.twelvet.api.system.domain.SysUser;
import com.twelvet.api.system.model.UserInfo;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 自定义用户信息处理
 */
@Service("TWTUserDetails")
public class TwTUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(TwTUserDetailsServiceImpl.class);

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 通过手机号密码模式进行登录
     *
     * @param phone    手机号码
     * @param password 密码
     * @return UserDetails
     */
    public UserDetails loadUserByPhoneAndPassword(String phone, String password) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new InvalidGrantException("无效的手机号码");
        }
        // TODO 模拟手机登录
        R<UserInfo> userResult = remoteUserService.getUserInfo("admin");
        auth(userResult, "admin");
        return getUserDetails(userResult);
    }

    /**
     * 用户名称登录
     *
     * @param username String
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        R<UserInfo> userResult = remoteUserService.getUserInfo(username);
        auth(userResult, username);
        return getUserDetails(userResult);
    }

    /**
     * 自定义账号状态检测
     *
     * @param userInfo userResult
     * @param username username
     */
    private void auth(R<UserInfo> userInfo, String username) {
        if (TWTUtils.isEmpty(userInfo) || TWTUtils.isEmpty(userInfo.getData())) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        // 获取用户状态信息
        SysUser sysUser = userInfo.getData().getSysUser();
        if (sysUser.getStatus().equals(0)) {
            log.info("{}： 用户已被冻结.", username);
            throw new TWTException("账号已被冻结");
        }
    }

    /**
     * 得到UserDetails
     *
     * @param result result
     * @return UserDetails
     */
    private UserDetails getUserDetails(R<UserInfo> result) {
        UserInfo info = result.getData();

        Set<String> dbAuthsSet = new HashSet<>();
        if (TWTUtils.isNotEmpty(info.getRoles())) {
            // 获取角色
            dbAuthsSet.addAll(info.getRoles());
            // 获取权限
            dbAuthsSet.addAll(info.getPermissions());
        }

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(
                dbAuthsSet.toArray(new String[0])
        );

        SysUser user = info.getSysUser();

        return new LoginUser(
                user.getUserId(),
                user.getDeptId(),
                user.getRoles(),
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }

}
