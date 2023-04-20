package com.twelvet.api.system.model;

import com.twelvet.api.system.domain.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 登录用户基本信息实体
 */
@Schema(description = "登录用户基本信息实体")
public class UserInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户基本信息
	 */
	@Schema(description = "用户基本信息")
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限标识集合")
	private Set<String> permissions;

	/**
	 * 角色集合
	 */
	@Schema(description = "角色集合")
	private Set<String> roles;

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
