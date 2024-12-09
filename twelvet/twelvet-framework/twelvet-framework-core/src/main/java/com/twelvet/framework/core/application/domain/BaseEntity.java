package com.twelvet.framework.core.application.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 实体基类
 */
public class BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 搜索值
	 */
	@Schema(description = "搜索值")
	@ExcelIgnore
	private String searchValue;

	/**
	 * 创建者
	 */
	@Schema(description = "创建者")
	@ExcelProperty("创建者")
	private String createBy;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@ExcelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@Schema(description = "更新者")
	@ExcelProperty("更新者")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@ExcelProperty("更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	@ExcelProperty("备注")
	private String remark;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@ExcelProperty("开始时间")
	private String beginTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@ExcelProperty("结束时间")
	private String endTime;

	/**
	 * 请求参数
	 */
	@Schema(description = "请求参数")
	@ExcelIgnore
	private Map<String, Object> params;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>(0);
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
