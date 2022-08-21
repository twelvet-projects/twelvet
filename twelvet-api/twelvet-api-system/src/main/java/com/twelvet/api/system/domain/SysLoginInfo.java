package com.twelvet.api.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twelvet.framework.core.application.domain.BaseEntity;
import com.twelvet.framework.utils.annotation.excel.Excel;
import com.twelvet.framework.utils.annotation.excel.Excel.ColumnType;

import java.util.Date;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统访问记录表 sys_login_info
 */
public class SysLoginInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Excel(name = "序号", cellType = ColumnType.NUMERIC)
	private Long infoId;

	/**
	 * 用户账号
	 */
	@Excel(name = "用户账号")
	private String userName;

	/**
	 * 状态 0成功 1失败
	 */
	@Excel(name = "状态", readConverterExp = "1=登录成功,2=退出成功,0=登录失败")
	private Integer status;

	/**
	 * 地址
	 */
	@Excel(name = "地址")
	private String ipaddr;

	/**
	 * 描述
	 */
	@Excel(name = "描述")
	private String msg;

	/**
	 * 访问时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date accessTime;

	/**
	 * 部门ID
	 */
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
