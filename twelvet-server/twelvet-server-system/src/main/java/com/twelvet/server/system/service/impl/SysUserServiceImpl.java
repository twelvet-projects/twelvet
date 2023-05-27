package com.twelvet.server.system.service.impl;

import com.twelvet.framework.datascope.annotation.SysDataScope;
import com.twelvet.api.system.domain.*;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.server.system.mapper.*;
import com.twelvet.server.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 用户信息
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

	private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysPostMapper sysPostMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	private SysUserPostMapper sysUserPostMapper;

	@Autowired
	private SysDeptMapper sysDeptMapper;

	/**
	 * 根据条件分页查询用户列表
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	@SysDataScope(deptAlias = "d", userAlias = "u")
	public List<SysUser> selectUserList(SysUser user) {
		return sysUserMapper.selectUserList(user);
	}

	/**
	 * 通过用户名查询用户(此接口会隐藏部分信息,请对号入座使用)
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserByUserName(String userName, boolean hidden) {
		SysUser sysUser = sysUserMapper.selectUserByUserName(userName);
		Long deptId = sysUser.getDeptId();
		SysDept sysDept = sysDeptMapper.selectDeptById(deptId);
		sysUser.setDept(sysDept);
		if (hidden) {
			// 隐藏手机号码/邮箱
			String phoneNumber = sysUser.getPhonenumber();
			String email = sysUser.getEmail();

			String phoneNumberHide = StringUtils.hidePhone(phoneNumber);
			String emailHide = StringUtils.hideEmail(email);

			sysUser.setPhonenumber(phoneNumberHide);
			sysUser.setEmail(emailHide);

			// 隐藏密码
			sysUser.setPassword(null);
		}

		return sysUser;
	}

	/**
	 * 通过用户ID查询用户
	 * @param userId 用户ID
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserById(Long userId) {
		return sysUserMapper.selectUserById(userId);
	}

	/**
	 * 查询用户所属角色组
	 * @param userName 用户名
	 * @return 结果
	 */
	@Override
	public String selectUserRoleGroup(String userName) {
		List<SysRole> list = sysRoleMapper.selectRolesByUserName(userName);
		StringBuilder idsStr = new StringBuilder();
		for (SysRole role : list) {
			idsStr.append(role.getRoleName()).append(",");
		}
		if (TUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * 查询用户所属岗位组
	 * @param userName 用户名
	 * @return 结果
	 */
	@Override
	public String selectUserPostGroup(String userName) {
		List<SysPost> list = sysPostMapper.selectPostsByUserName(userName);
		StringBuilder idsStr = new StringBuilder();
		for (SysPost post : list) {
			idsStr.append(post.getPostName()).append(",");
		}
		if (TUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * 校验用户名称是否唯一
	 * @param userName 用户名称
	 * @return 结果
	 */
	@Override
	public String checkUserNameUnique(String userName) {
		int count = sysUserMapper.checkUserNameUnique(userName);
		if (count > 0) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验用户名称是否唯一
	 * @param user 用户信息
	 * @return String
	 */
	@Override
	public String checkPhoneUnique(SysUser user) {
		Long userId = TUtils.isEmpty(user.getUserId()) ? -1L : user.getUserId();
		SysUser info = sysUserMapper.checkPhoneUnique(user.getPhonenumber());
		if (TUtils.isNotEmpty(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验email是否唯一
	 * @param user 用户信息
	 * @return String
	 */
	@Override
	public String checkEmailUnique(SysUser user) {
		Long userId = TUtils.isEmpty(user.getUserId()) ? -1L : user.getUserId();
		SysUser info = sysUserMapper.checkEmailUnique(user.getEmail());
		if (TUtils.isNotEmpty(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验用户是否允许操作
	 * @param user 用户信息
	 */
	@Override
	public void checkUserAllowed(SysUser user) {
		if (TUtils.isNotEmpty(user.getUserId()) && user.isAdmin()) {
			throw new TWTException("不允许操作超级管理员用户");
		}
	}

	/**
	 * 校验用户是否有数据权限
	 * @param userId 用户id
	 */
	@Override
	public void checkUserDataScope(Long userId) {
		if (!SysUser.isAdmin(SecurityUtils.getLoginUser().getUserId())) {
			SysUser user = new SysUser();
			user.setUserId(userId);
			List<SysUser> users = SpringContextHolder.getAopProxy(this).selectUserList(user);
			if (StringUtils.isEmpty(users)) {
				throw new TWTException("没有权限访问用户数据！");
			}
		}
	}

	/**
	 * 新增保存用户信息
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	public int insertUser(SysUser user) {
		// 新增用户信息
		int rows = sysUserMapper.insertUser(user);
		// 新增用户岗位关联
		insertUserPost(user);
		// 新增用户与角色管理
		insertUserRole(user);
		return rows;
	}

	/**
	 * 修改保存用户信息
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = TWTException.class)
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#user.username")
	public int updateUser(SysUser user) {
		Long userId = user.getUserId();
		// 删除用户与角色关联
		sysUserRoleMapper.deleteUserRoleByUserId(userId);
		// 新增用户与角色管理
		insertUserRole(user);
		// 删除用户与岗位关联
		sysUserPostMapper.deleteUserPostByUserId(userId);
		// 新增用户与岗位管理
		insertUserPost(user);
		return sysUserMapper.updateUser(user);
	}

	/**
	 * 修改用户状态
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int updateUserStatus(SysUser user) {
		return sysUserMapper.updateUser(user);
	}

	/**
	 * 修改用户基本信息
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#user.username")
	public int updateUserProfile(SysUser user) {
		return sysUserMapper.updateUser(user);
	}

	/**
	 * 修改用户头像
	 * @param userName 用户名
	 * @param avatar 头像地址
	 * @return 结果
	 */
	@Override
	public boolean updateUserAvatar(String userName, String avatar) {
		return sysUserMapper.updateUserAvatar(userName, avatar) > 0;
	}

	/**
	 * 重置用户密码
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int resetPwd(SysUser user) {
		return sysUserMapper.updateUser(user);
	}

	/**
	 * 重置用户密码
	 * @param userName 用户名
	 * @param password 密码
	 * @return 结果
	 */
	@Override
	public int resetUserPwd(String userName, String password) {
		return sysUserMapper.resetUserPwd(userName, password);
	}

	/**
	 * 新增用户角色信息
	 * @param user 用户对象
	 */
	public void insertUserRole(SysUser user) {
		Long[] roles = user.getRoleIds();
		if (TUtils.isNotEmpty(roles)) {
			// 新增用户与角色管理
			List<SysUserRole> list = new ArrayList<>();
			for (Long roleId : roles) {
				SysUserRole ur = new SysUserRole();
				ur.setUserId(user.getUserId());
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				sysUserRoleMapper.batchUserRole(list);
			}
		}
	}

	/**
	 * 新增用户岗位信息
	 * @param user 用户对象
	 */
	public void insertUserPost(SysUser user) {
		Long[] posts = user.getPostIds();
		if (TUtils.isNotEmpty(posts)) {
			// 新增用户与岗位管理
			List<SysUserPost> list = new ArrayList<SysUserPost>();
			for (Long postId : posts) {
				SysUserPost up = new SysUserPost();
				up.setUserId(user.getUserId());
				up.setPostId(postId);
				list.add(up);
			}
			if (list.size() > 0) {
				sysUserPostMapper.batchUserPost(list);
			}
		}
	}

	/**
	 * 通过用户ID删除用户
	 * @param userId 用户ID
	 * @return 结果
	 */
	@Override
	public int deleteUserById(Long userId) {
		// 删除用户与角色关联
		sysUserRoleMapper.deleteUserRoleByUserId(userId);
		// 删除用户与岗位表
		sysUserPostMapper.deleteUserPostByUserId(userId);
		return sysUserMapper.deleteUserById(userId);
	}

	/**
	 * 批量删除用户信息
	 * @param userIds 需要删除的用户ID
	 * @return 结果
	 */
	@Override
	public int deleteUserByIds(Long[] userIds) {
		for (Long userId : userIds) {
			checkUserAllowed(new SysUser(userId));
			checkUserDataScope(userId);
		}
		return sysUserMapper.deleteUserByIds(userIds);
	}

	/**
	 * 导入用户数据
	 * @param userList 用户数据列表
	 * @param cover 更新操作人
	 * @param operName 操作用户
	 * @return 结果
	 */
	@Override
	public String importUser(List<SysUser> userList, Boolean cover, String operName) {
		if (TUtils.isEmpty(userList) || userList.size() == 0) {
			throw new TWTException("导入用户数据不能为空！");
		}
		int successNum = 0;
		int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		for (SysUser user : userList) {
			try {
				// 验证是否存在这个用户
				SysUser u = sysUserMapper.selectUserByUserName(user.getUsername());
				if (TUtils.isEmpty(u)) {
					// 初始化密码为123456
					user.setPassword(SecurityUtils.encryptPassword("123456"));
					user.setCreateBy(operName);
					this.insertUser(user);
					successNum++;
					successMsg.append("<br/>")
						.append(successNum)
						.append("、账号 ")
						.append(user.getUsername())
						.append(" 导入成功");
				}
				else if (cover) {
					user.setUpdateBy(operName);
					this.updateUser(user);
					successNum++;
					successMsg.append("<br/>")
						.append(successNum)
						.append("、账号 ")
						.append(user.getUsername())
						.append(" 更新成功");
				}
				else {
					failureNum++;
					failureMsg.append("<br/>")
						.append(failureNum)
						.append("、账号 ")
						.append(user.getUsername())
						.append(" 已存在");
				}
			}
			catch (Exception e) {
				failureNum++;
				String msg = "<br/>" + failureNum + "、账号 " + user.getUsername() + " 导入失败：";
				failureMsg.append(msg).append(e.getMessage());
				log.error(msg, e);
			}
		}
		if (failureNum > 0) {
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
			throw new TWTException(failureMsg.toString());
		}
		else {
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
		}
		return successMsg.toString();
	}

}
