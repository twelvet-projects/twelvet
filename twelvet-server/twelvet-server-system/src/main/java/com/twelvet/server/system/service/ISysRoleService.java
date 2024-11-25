package com.twelvet.server.system.service;

import com.twelvet.api.system.domain.SysRole;

import java.util.List;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 角色权限服务
 */
public interface ISysRoleService {

	/**
	 * 根据条件分页查询角色数据
	 * @param role 角色信息
	 * @return 角色数据集合信息
	 */
	List<SysRole> selectRoleList(SysRole role);

	/**
	 * 根据用户ID查询角色
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	Set<String> selectRolePermissionByUserId(Long userId);

	/**
	 * 查询所有角色
	 * @return 角色列表
	 */
	List<SysRole> selectRoleAll();

	/**
	 * 根据用户ID获取角色选择框列表
	 * @param userId 用户ID
	 * @return 选中角色ID列表
	 */
	List<Integer> selectRoleListByUserId(Long userId);

	/**
	 * 通过角色ID查询角色
	 * @param roleId 角色ID
	 * @return 角色对象信息
	 */
	SysRole selectRoleById(Long roleId);

	/**
	 * 校验角色名称是否唯一
	 * @param role 角色信息
	 * @return 结果
	 */
	String checkRoleNameUnique(SysRole role);

	/**
	 * 校验角色权限是否唯一
	 * @param role 角色信息
	 * @return 结果
	 */
	String checkRoleKeyUnique(SysRole role);

	/**
	 * 校验角色是否允许操作
	 * @param role 角色信息
	 */
	void checkRoleAllowed(SysRole role);

	/**
	 * 校验角色是否有数据权限
	 * @param roleId 角色id
	 */
	void checkRoleDataScope(Long roleId);

	/**
	 * 通过角色ID查询角色使用数量
	 * @param roleId 角色ID
	 * @return 结果
	 */
	int countUserRoleByRoleId(Long roleId);

	/**
	 * 新增保存角色信息
	 * @param role 角色信息
	 * @return 结果
	 */
	int insertRole(SysRole role);

	/**
	 * 修改保存角色信息
	 * @param role 角色信息
	 * @return 结果
	 */
	int updateRole(SysRole role);

	/**
	 * 修改角色状态
	 * @param role 角色信息
	 * @return 结果
	 */
	int updateRoleStatus(SysRole role);

	/**
	 * 修改数据权限信息
	 * @param role 角色信息
	 * @return 结果
	 */
	int authDataScope(SysRole role);

	/**
	 * 通过角色ID删除角色
	 * @param roleId 角色ID
	 * @return 结果
	 */
	int deleteRoleById(Long roleId);

	/**
	 * 批量删除角色信息
	 * @param roleIds 需要删除的角色ID
	 * @return 结果
	 */
	int deleteRoleByIds(Long[] roleIds);

}
