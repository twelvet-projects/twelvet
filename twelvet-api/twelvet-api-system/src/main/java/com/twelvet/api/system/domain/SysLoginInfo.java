package com.twelvet.api.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twelvet.framework.core.application.domain.BaseEntity;
import com.twelvet.framework.utils.annotation.excel.Excel;
import com.twelvet.framework.utils.annotation.excel.Excel.ColumnType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Date;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 系统访问记录表 sys_login_info
 */
@Schema(description = "系统访问记录表")
public class SysLoginInfo extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(description = "序号")
	@Excel(name = "序号", cellType = ColumnType.NUMERIC)
	private Long infoId;

	/**
	 * 用户账号
	 */
	@Schema(description = "用户账号")
	@Excel(name = "用户账号")
	private String userName;

	/**
	 * 状态 0成功 1失败
	 */
	@Schema(description = "状态")
	@Excel(name = "状态", readConverterExp = "1=登录成功,2=退出成功,0=登录失败")
	private String status;

	/**
	 * 地址
	 */
	@Schema(description = "地址")
	@Excel(name = "地址")
	private String ipaddr;

	/**
	 * 描述
	 */
	@Schema(description = "描述")
	@Excel(name = "描述")
	private String msg;

	/**
	 * 访问时间
	 */
	@Schema(description = "访问时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date accessTime;

	/**
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	@Excel(name = "部门ID")
	private Long deptId;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	@Override
	public String toString() {
		return "SysLoginInfo{" + "infoId=" + infoId + ", userName='" + userName + '\'' + ", status=" + status
				+ ", ipaddr='" + ipaddr + '\'' + ", msg='" + msg + '\'' + ", accessTime=" + accessTime + ", deptId='"
				+ deptId + '\'' + '}';
	}

}
