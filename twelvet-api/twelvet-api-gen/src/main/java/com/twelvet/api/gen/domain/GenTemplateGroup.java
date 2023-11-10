package com.twelvet.api.gen.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * 模板分组关联对象 gen_template_group
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Schema(description = "模板分组关联对象")
public class GenTemplateGroup extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/** 分组id */
	@Schema(description = "分组id")
	private Long groupId;

	/** 模板id */
	@Schema(description = "模板id")
	private Long templateId;

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("groupId", getGroupId())
			.append("templateId", getTemplateId())
			.toString();
	}

}
