package com.twelvet.framework.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.twelvet.api.system.domain.SysUser;
import com.twelvet.api.system.model.UserInfo;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.utils.$;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 自定义实现spring security用户体系
 */
public interface TwUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(R<UserInfo> result) {
		UserInfo info = result.getData();

		Set<String> dbAuthsSet = new HashSet<>();
		if ($.isNotEmpty(info.getRoles())) {
			// 获取角色
			dbAuthsSet.addAll(info.getRoles());
			// 获取权限
			dbAuthsSet.addAll(info.getPermissions());
		}

		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthsSet.toArray(new String[0]));

		SysUser user = info.getSysUser();

		return new LoginUser(user.getUserId(), user.getDeptId(), user.getRoles(), user.getUsername(),
				user.getPassword(), true, true, true, true, authorities);
	}

	/**
	 * 通过用户实体查询
	 * @param loginUser user
	 * @return UserDetails
	 */
	default UserDetails loadUserByUser(LoginUser loginUser) {
		return this.loadUserByUsername(loginUser.getUsername());
	}

}