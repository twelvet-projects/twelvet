package com.twelvet.server.system.service.impl;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.api.system.domain.SysUser;
import com.twelvet.api.system.domain.vo.TreeSelect;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.datascope.annotation.SysDataScope;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.system.mapper.SysDeptMapper;
import com.twelvet.server.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 部门管理 服务实现
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

	@Autowired
	private SysDeptMapper deptMapper;

	/**
	 * 查询部门管理数据
	 * @param dept 部门信息
	 * @return 部门信息集合
	 */
	@Override
	@SysDataScope(deptAlias = "d")
	public List<SysDept> selectDeptList(SysDept dept) {
		return deptMapper.selectDeptList(dept);
	}

	/**
	 * 构建前端所需要树结构
	 * @param depts 部门列表
	 * @return 树结构列表
	 */
	@Override
	public List<SysDept> buildDeptTree(List<SysDept> depts) {
		List<SysDept> returnList = new ArrayList<>();
		List<Long> tempList = new ArrayList<>();
		for (SysDept dept : depts) {
			tempList.add(dept.getDeptId());
		}
		for (SysDept dept : depts) {
			// 如果是顶级节点, 遍历该父节点的所有子节点
			if (!tempList.contains(dept.getParentId())) {
				recursionFn(depts, dept);
				returnList.add(dept);
			}
		}
		if (returnList.isEmpty()) {
			returnList = depts;
		}
		return returnList;
	}

	/**
	 * 构建前端所需要下拉树结构
	 * @param depts 部门列表
	 * @return 下拉树结构列表
	 */
	@Override
	public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
		List<SysDept> deptTrees = buildDeptTree(depts);
		return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 根据角色ID查询部门树信息
	 * @param roleId 角色ID
	 * @return 选中部门列表
	 */
	@Override
	public List<Integer> selectDeptListByRoleId(Long roleId) {
		return deptMapper.selectDeptListByRoleId(roleId);
	}

	/**
	 * 根据部门ID查询信息
	 * @param deptId 部门ID
	 * @return 部门信息
	 */
	@Override
	public SysDept selectDeptById(Long deptId) {
		return deptMapper.selectDeptById(deptId);
	}

	/**
	 * 根据ID查询所有子部门（正常状态）
	 * @param deptId 部门ID
	 * @return 子部门数
	 */
	@Override
	public int selectNormalChildrenDeptById(Long deptId) {
		return deptMapper.selectNormalChildrenDeptById(deptId);
	}

	/**
	 * 是否存在子节点
	 * @param deptId 部门ID
	 * @return 结果
	 */
	@Override
	public boolean hasChildByDeptId(Long deptId) {
		int result = deptMapper.hasChildByDeptId(deptId);
		return result > 0;
	}

	/**
	 * 查询部门是否存在用户
	 * @param deptId 部门ID
	 * @return 结果 true 存在 false 不存在
	 */
	@Override
	public boolean checkDeptExistUser(Long deptId) {
		int result = deptMapper.checkDeptExistUser(deptId);
		return result > 0;
	}

	/**
	 * 校验部门名称是否唯一
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public String checkDeptNameUnique(SysDept dept) {
		Long deptId = TUtils.isEmpty(dept.getDeptId()) ? -1L : dept.getDeptId();
		SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
		if (TUtils.isNotEmpty(info) && info.getDeptId().longValue() != deptId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验部门是否有数据权限
	 * @param deptId 部门id
	 */
	@Override
	public void checkDeptDataScope(Long deptId) {
		if (!SysUser.isAdmin(SecurityUtils.getLoginUser().getUserId())) {
			SysDept dept = new SysDept();
			dept.setDeptId(deptId);
			List<SysDept> depts = SpringContextHolder.getAopProxy(this).selectDeptList(dept);
			if (StringUtils.isEmpty(depts)) {
				throw new TWTException("没有权限访问部门数据！");
			}
		}
	}

	/**
	 * 新增保存部门信息
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int insertDept(SysDept dept) {

		if (dept.getParentId() != 0) {
			SysDept info = deptMapper.selectDeptById(dept.getParentId());
			// 如果父节点不为正常状态,则不允许新增子节点
			if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
				throw new TWTException("部门停用，不允许新增");
			}
			dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		}
		else {
			dept.setAncestors("0");
		}

		return deptMapper.insertDept(dept);
	}

	/**
	 * 修改保存部门信息
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int updateDept(SysDept dept) {
		SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
		SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
		if (TUtils.isNotEmpty(newParentDept) && TUtils.isNotEmpty(oldDept)) {
			String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
			String oldAncestors = oldDept.getAncestors();
			dept.setAncestors(newAncestors);
			updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
		}
		int result = deptMapper.updateDept(dept);
		if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
			// 如果该部门是启用状态，则启用该部门的所有上级部门
			updateParentDeptStatus(dept);
		}
		return result;
	}

	/**
	 * 修改该部门的父级部门状态
	 * @param dept 当前部门
	 */
	private void updateParentDeptStatus(SysDept dept) {
		String updateBy = dept.getUpdateBy();
		dept = deptMapper.selectDeptById(dept.getDeptId());
		dept.setUpdateBy(updateBy);
		deptMapper.updateDeptStatus(dept);
	}

	/**
	 * 修改子元素关系
	 * @param deptId 被修改的部门ID
	 * @param newAncestors 新的父ID集合
	 * @param oldAncestors 旧的父ID集合
	 */
	public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
		List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
		for (SysDept child : children) {
			child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
		}
		if (children.size() > 0) {
			deptMapper.updateDeptChildren(children);
		}
	}

	/**
	 * 删除部门管理信息
	 * @param deptId 部门ID
	 * @return 结果
	 */
	@Override
	public int deleteDeptById(Long deptId) {
		return deptMapper.deleteDeptById(deptId);
	}

	/**
	 * 递归列表
	 */
	private void recursionFn(List<SysDept> list, SysDept t) {
		// 得到子节点列表
		List<SysDept> childList = getChildList(list, t);
		t.setChildren(childList);
		for (SysDept tChild : childList) {
			if (hasChild(list, tChild)) {
				// 判断是否有子节点
				for (SysDept n : childList) {
					recursionFn(list, n);
				}
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
		List<SysDept> tlist = new ArrayList<SysDept>();
		for (SysDept n : list) {
			if (TUtils.isNotEmpty(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysDept> list, SysDept t) {
		return getChildList(list, t).size() > 0;
	}

}
