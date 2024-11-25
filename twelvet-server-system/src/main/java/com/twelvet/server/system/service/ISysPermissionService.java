package com.twelvet.server.system.service;

import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 权限服务
 */
public interface ISysPermissionService {

	/**
	 * 获取角色数据权限
	 * @param userId 用户ID
	 * @return 角色权限信息
	 */
	Set<String> getRolePermission(Long userId);

	/**
	 * 获取菜单权限
	 * @param userId 用户ID
	 * @return 菜单权限信息
	 */
	Set<String> getMenuPermission(Long userId);

}
