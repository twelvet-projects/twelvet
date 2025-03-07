package com.twelvet.api.system.domain;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.util.Date;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: sys_user实体
 */
@Schema(description = "sys_user实体")
public class SysUser extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	@ExcelProperty(value = "用户序号")
	private Long userId;

	/**
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	@ExcelProperty(value = "部门编号")
	private Long deptId;

	/**
	 * 用户账号
	 */
	@Schema(description = "用户账号")
	@ExcelProperty(value = "登录名称")
	private String username;

	/**
	 * 用户昵称
	 */
	@Schema(description = "用户昵称")
	@ExcelProperty(value = "用户名称")
	private String nickName;

	/**
	 * 用户邮箱
	 */
	@Schema(description = "用户邮箱")
	@ExcelProperty(value = "用户邮箱")
	private String email;

	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码")
	@ExcelProperty(value = "手机号码")
	private String phonenumber;

	/**
	 * 用户性别
	 */
	@Schema(description = "用户性别")
	@ExcelProperty(value = "用户性别(0=男,1=女,2=未知)")
	private String sex;

	/**
	 * 用户头像
	 */
	@Schema(description = "用户头像")
	private String avatar;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;

	/**
	 * 帐号状态（0正常 1停用）
	 */
	@Schema(description = "帐号状态")
	@ExcelProperty(value = "帐号状态(0=正常,1=停用)")
	private String status;

	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	@Schema(description = "删除标志")
	private String delFlag;

	/**
	 * 最后登录IP
	 */
	@Schema(description = "最后登录IP")
	@ExcelProperty(value = "最后登录IP")
	private String loginIp;

	/**
	 * 最后登录时间
	 */
	@Schema(description = "最后登录时间")
	@ExcelProperty(value = "最后登录时间")
	private Date loginDate;

	/**
	 * 部门对象
	 */
	@ExcelIgnore
	@Schema(description = "部门对象")
	private SysDept dept;

	/**
	 * 角色对象
	 */
	@ExcelIgnore
	@Schema(description = "角色对象")
	private List<SysRole> roles;

	/**
	 * 角色组
	 */
	@ExcelIgnore
	@Schema(description = "角色组")
	private Long[] roleIds;

	/**
	 * 岗位组
	 */
	@ExcelIgnore
	@Schema(description = "岗位组")
	private Long[] postIds;

	/**
	 * 角色ID
	 */
	@Schema(description = "用户ID")
	private Long roleId;

	public SysUser() {

	}

	public SysUser(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isAdmin() {
		return isAdmin(this.userId);
	}

	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@NotBlank(message = "用户账号不能为空")
	@Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long[] getPostIds() {
		return postIds;
	}

	public void setPostIds(Long[] postIds) {
		this.postIds = postIds;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
			.append("deptId", getDeptId())
			.append("username", getUsername())
			.append("nickName", getNickName())
			.append("email", getEmail())
			.append("phonenumber", getPhonenumber())
			.append("sex", getSex())
			.append("avatar", getAvatar())
			.append("password", getPassword())
			.append("status", getStatus())
			.append("delFlag", getDelFlag())
			.append("loginIp", getLoginIp())
			.append("loginDate", getLoginDate())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("remark", getRemark())
			.append("dept", getDept())
			.toString();
	}

}
