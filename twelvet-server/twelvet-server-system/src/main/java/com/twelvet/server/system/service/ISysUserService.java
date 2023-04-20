package com.twelvet.server.system.service;

import com.twelvet.api.system.domain.SysUser;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 用户信息
 */
public interface ISysUserService {

	/**
	 * 根据条件分页查询用户列表
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	List<SysUser> selectUserList(SysUser user);

	/**
	 * 通过用户名查询用户
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	SysUser selectUserByUserName(String userName, boolean hidden);

	/**
	 * 通过用户ID查询用户
	 * @param userId 用户ID
	 * @return 用户对象信息
	 */
	SysUser selectUserById(Long userId);

	/**
	 * 根据用户ID查询用户所属角色组
	 * @param userName 用户名
	 * @return 结果
	 */
	String selectUserRoleGroup(String userName);

	/**
	 * 根据用户ID查询用户所属岗位组
	 * @param userName 用户名
	 * @return 结果
	 */
	String selectUserPostGroup(String userName);

	/**
	 * 校验用户名称是否唯一
	 * @param userName 用户名称
	 * @return 结果
	 */
	String checkUserNameUnique(String userName);

	/**
	 * 校验手机号码是否唯一
	 * @param user 用户信息
	 * @return 结果
	 */
	String checkPhoneUnique(SysUser user);

	/**
	 * 校验email是否唯一
	 * @param user 用户信息
	 * @return 结果
	 */
	String checkEmailUnique(SysUser user);

	/**
	 * 校验用户是否允许操作
	 * @param user 用户信息
	 */
	void checkUserAllowed(SysUser user);

	/**
	 * 校验用户是否有数据权限
	 * @param userId 用户id
	 */
	void checkUserDataScope(Long userId);

	/**
	 * 新增用户信息
	 * @param user 用户信息
	 * @return 结果
	 */
	int insertUser(SysUser user);

	/**
	 * 修改用户信息
	 * @param user 用户信息
	 * @return 结果
	 */
	int updateUser(SysUser user);

	/**
	 * 修改用户状态
	 * @param user 用户信息
	 * @return 结果
	 */
	int updateUserStatus(SysUser user);

	/**
	 * 修改用户基本信息
	 * @param user 用户信息
	 * @return 结果
	 */
	int updateUserProfile(SysUser user);

	/**
	 * 修改用户头像
	 * @param userName 用户名
	 * @param avatar 头像地址
	 * @return 结果
	 */
	boolean updateUserAvatar(String userName, String avatar);

	/**
	 * 重置用户密码
	 * @param user 用户信息
	 * @return 结果
	 */
	int resetPwd(SysUser user);

	/**
	 * 重置用户密码
	 * @param userName 用户名
	 * @param password 密码
	 * @return 结果
	 */
	int resetUserPwd(String userName, String password);

	/**
	 * 通过用户ID删除用户
	 * @param userId 用户ID
	 * @return 结果
	 */
	int deleteUserById(Long userId);

	/**
	 * 批量删除用户信息
	 * @param userIds 需要删除的用户ID
	 * @return 结果
	 */
	int deleteUserByIds(Long[] userIds);

	/**
	 * 导入用户数据
	 * @param userList 用户数据列表
	 * @param cover 是否更新支持，如果已存在，则进行更新数据
	 * @param operName 操作用户
	 * @return 结果
	 */
	String importUser(List<SysUser> userList, Boolean cover, String operName);

}
