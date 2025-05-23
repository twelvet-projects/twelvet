package com.twelvet.api.system.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;

@Schema(description = "字典表")
public class SysDictType extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 字典主键
	 */
	@Schema(description = "字典主键")
	@ExcelProperty(value = "字典主键")
	private Long dictId;

	/**
	 * 字典名称
	 */
	@Schema(description = "字典名称")
	@ExcelProperty(value = "字典名称")
	private String dictName;

	/**
	 * 字典类型
	 */
	@Schema(description = "字典类型")
	@ExcelProperty(value = "字典类型")
	private String dictType;

	/**
	 * 状态（0正常 1停用）
	 */
	@Schema(description = "状态")
	@ExcelProperty(value = "状态(0=正常,1=停用)")
	private String status;

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	@NotBlank(message = "字典名称不能为空")
	@Size(max = 100, message = "字典类型名称长度不能超过100个字符")
	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	@NotBlank(message = "字典类型不能为空")
	@Size(max = 100, message = "字典类型类型长度不能超过100个字符")
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("dictId", getDictId())
			.append("dictName", getDictName())
			.append("dictType", getDictType())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("remark", getRemark())
			.toString();
	}

}