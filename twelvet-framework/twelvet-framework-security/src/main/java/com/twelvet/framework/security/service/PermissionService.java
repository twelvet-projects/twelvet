package com.twelvet.framework.security.service;

import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.TUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 自定义权限实现
 */
public class PermissionService {

	/**
	 * 所有权限标识
	 */
	private static final String ALL_PERMISSION = "*:*:*";

	/**
	 * 管理员角色权限标识
	 */
	private static final String SUPER_ADMIN = "admin";

	private static final String ROLE_DELIMETER = ",";

	private static final String PERMISSION_DELIMETER = ",";

	/**
	 * 验证用户是否具备某权限
	 * @param permission 权限字符串
	 * @return 用户是否具备某权限
	 */
	public boolean hasPermi(String permission) {
		if (TUtils.isEmpty(permission)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (TUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
			return false;
		}
		return hasPermissions(loginUser.getAuthorities(), permission);
	}

	/**
	 * 验证用户是否不具备某权限，与 hasPermi逻辑相反
	 * @param permission 权限字符串
	 * @return 用户是否不具备某权限
	 */
	public boolean lacksPermi(String permission) {
		return !hasPermi(permission);
	}

	/**
	 * 验证用户是否具有以下任意一个权限
	 * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
	 * @return 用户是否具有以下任意一个权限
	 */
	public boolean hasAnyPermi(String permissions) {
		if (TUtils.isEmpty(permissions)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (TUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();
		for (String permission : permissions.split(PERMISSION_DELIMETER)) {
			if (permission != null && hasPermissions(authorities, permission)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断用户是否拥有某个角色
	 * @param role 角色字符串
	 * @return 用户是否具备某角色
	 */
	public boolean hasRole(String role) {
		if (TUtils.isEmpty(role)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (TUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
			return false;
		}
		for (GrantedAuthority authorities : loginUser.getAuthorities()) {
			String roleKey = authorities.getAuthority();
			if (SUPER_ADMIN.contains(roleKey) || roleKey.contains(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证用户是否不具备某角色，与 isRole逻辑相反。
	 * @param role 角色名称
	 * @return 用户是否不具备某角色
	 */
	public boolean lacksRole(String role) {
		return !hasRole(role);
	}

	/**
	 * 验证用户是否具有以下任意一个角色
	 * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
	 * @return 用户是否具有以下任意一个角色
	 */
	public boolean hasAnyRoles(String roles) {
		if (TUtils.isEmpty(roles)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (TUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
			return false;
		}
		for (String role : roles.split(ROLE_DELIMETER)) {
			if (hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否包含权限
	 * @param authorities 用户拥有的权限列表
	 * @param permission 需验证的权限权限字符串
	 * @return 用户是否具备某权限
	 */
	private boolean hasPermissions(Collection<? extends GrantedAuthority> authorities, String permission) {
		return authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.filter(StringUtils::hasText)
			.anyMatch(x -> ALL_PERMISSION.contains(x) || PatternMatchUtils.simpleMatch(permission, x));
	}

}
