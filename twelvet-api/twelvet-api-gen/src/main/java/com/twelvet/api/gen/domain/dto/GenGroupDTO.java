package com.twelvet.api.gen.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.List;

/**
 * 模板分组对象VO
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Schema(description = "模板分组对象VO")
public class GenGroupDTO extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 分组名称
	 */
	@Schema(description = "分组名称")
	@ExcelProperty(value = "分组名称")
	private String groupName;

	/**
	 * 分组描述
	 */
	@Schema(description = "分组描述")
	@ExcelProperty(value = "分组描述")
	private String groupDesc;

	/**
	 * 模板ids
	 */
	@Schema(description = "拥有的模板列表")
	private List<Long> templateIdList;

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

	public List<Long> getTemplateIdList() {
		return templateIdList;
	}

	public void setTemplateIdList(List<Long> templateIdList) {
		this.templateIdList = templateIdList;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
			.append("groupName", getGroupName())
			.append("groupDesc", getGroupDesc())
			.append("templateIdList", getTemplateIdList())
			.append("createBy", getCreateBy())
			.append("updateBy", getUpdateBy())
			.append("createTime", getCreateTime())
			.append("updateTime", getUpdateTime())
			.toString();
	}

}
