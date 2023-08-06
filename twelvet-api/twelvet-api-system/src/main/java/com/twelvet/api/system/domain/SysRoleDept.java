package com.twelvet.api.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 角色和部门关联 sys_role_dept
 */
@Schema(description = "角色和部门关联")
public class SysRoleDept implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/** 角色ID */
	@Schema(description = "角色ID")
	private Long roleId;

	/** 部门ID */
	@Schema(description = "部门ID")
	private Long deptId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("roleId", getRoleId())
			.append("deptId", getDeptId())
			.toString();
	}

}
