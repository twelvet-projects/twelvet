package com.twelvet.api.gen.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * 模板分组对象 gen_group
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2023-11-06
 */
@Schema(description = "模板分组对象")
public class GenGroup extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/** $column.columnComment */
	@Schema(description = "$column.columnComment")
	private Long id;

	/** 分组名称 */
	@Schema(description = "分组名称")
	@ExcelProperty(value = "分组名称")
	private String groupName;

	/** 分组描述 */
	@Schema(description = "分组描述")
	@ExcelProperty(value = "分组描述")
	private String groupDesc;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
			.append("groupName", getGroupName())
			.append("groupDesc", getGroupDesc())
			.append("createBy", getCreateBy())
			.append("updateBy", getUpdateBy())
			.append("createTime", getCreateTime())
			.append("updateTime", getUpdateTime())
			.toString();
	}

}
