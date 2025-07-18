package com.twelvet.framework.datascope.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 分布式数据权限VO
 */
public class DataScopeVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 部门ID列表
	 */
	private Set<Long> deptIdSet;

	public Set<Long> getDeptIdSet() {
		return deptIdSet;
	}

	public void setDeptIdSet(Set<Long> deptIdSet) {
		this.deptIdSet = deptIdSet;
	}

	@Override
	public String toString() {
		return "DataScopeVO{" + "deptIdSet=" + deptIdSet + '}';
	}

}
