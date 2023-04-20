package com.twelvet.api.system.domain.params;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 用户修改密码参数
 */
@Schema(description = "用户修改密码参数")
public class UserPassword implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 旧密码
	 */
	@Schema(description = "旧密码")
	private String oldPassword;

	/**
	 * 新密码
	 */
	@Schema(description = "新密码")
	private String newPassword;

	/**
	 * 确认密码
	 */
	@Schema(description = "确认密码")
	private String confirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UserPassword{" + "oldPassword='" + oldPassword + '\'' + ", newPassword='" + newPassword + '\''
				+ ", confirmPassword='" + confirmPassword + '\'' + '}';
	}

}
