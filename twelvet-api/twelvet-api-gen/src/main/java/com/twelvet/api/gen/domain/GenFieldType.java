package com.twelvet.api.gen.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字段类型管理对象 gen_field_type
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Schema(description = "字段类型管理对象")
public class GenFieldType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** id */
	@Schema(description = "id")
	private Long id;

	/** 字段类型 */
	@Schema(description = "字段类型")
	@ExcelProperty(value = "字段类型")
	private String columnType;

	/** 属性类型 */
	@Schema(description = "属性类型")
	@ExcelProperty(value = "属性类型")
	private String attrType;

	/** 属性包名 */
	@Schema(description = "属性包名")
	@ExcelProperty(value = "属性包名")
	private String packageName;

	/** 删除标记 */
	@Schema(description = "删除标记")
	private String delFlag;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
			.append("columnType", getColumnType())
			.append("attrType", getAttrType())
			.append("packageName", getPackageName())
			.append("createTime", getCreateTime())
			.append("createBy", getCreateBy())
			.append("updateTime", getUpdateTime())
			.append("updateBy", getUpdateBy())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
