package com.twelvet.api.system.domain;

import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Arrays;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 角色表 sys_role
 */
@Schema(description = "角色表")
public class SysRole extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private Long roleId;

	/**
	 * 角色名称
	 */
	@Schema(description = "角色名称")
	private String roleName;

	/**
	 * 角色权限
	 */
	@Schema(description = "角色权限")
	private String roleKey;

	/**
	 * 角色排序
	 */
	@Schema(description = "角色排序")
	private String roleSort;

	/**
	 * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）
	 */
	@Schema(description = "数据范围")
	private String dataScope;

	/**
	 * 角色状态（0正常 1停用）
	 */
	@Schema(description = "角色状态")
	private String status;

	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	@Schema(description = "删除标志")
	private String delFlag;

	/**
	 * 用户是否存在此角色标识 默认不存在
	 */
	@Schema(description = "角色标识")
	private boolean flag = false;

	/**
	 * 菜单组
	 */
	@Schema(description = "菜单组")
	private Long[] menuIds;

	/**
	 * 部门组（数据权限）
	 */
	@Schema(description = "部门组")
	private Long[] deptIds;

	public SysRole() {

	}

	public SysRole(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isAdmin() {
		return isAdmin(this.roleId);
	}

	public static boolean isAdmin(Long roleId) {
		return roleId != null && 1L == roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(String roleSort) {
		this.roleSort = roleSort;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long[] getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Long[] menuIds) {
		this.menuIds = menuIds;
	}

	public Long[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(Long[] deptIds) {
		this.deptIds = deptIds;
	}

	@Override
	public String toString() {
		return "SysRole{" + "roleId=" + roleId + ", roleName='" + roleName + '\'' + ", roleKey='" + roleKey + '\''
				+ ", roleSort='" + roleSort + '\'' + ", dataScope='" + dataScope + '\'' + ", status='" + status + '\''
				+ ", delFlag='" + delFlag + '\'' + ", flag=" + flag + ", menuIds=" + Arrays.toString(menuIds)
				+ ", deptIds=" + Arrays.toString(deptIds) + '}';
	}

}
