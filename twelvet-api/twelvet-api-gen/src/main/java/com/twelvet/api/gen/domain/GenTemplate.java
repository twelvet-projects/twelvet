package com.twelvet.api.gen.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模板对象 gen_template
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Schema(description = "模板对象")
public class GenTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Schema(description = "主键")
	private Long id;

	/** 模板名称 */
	@Schema(description = "模板名称")
	@ExcelProperty(value = "模板名称")
	private String templateName;

	/** 模板路径 */
	@Schema(description = "模板路径")
	@ExcelProperty(value = "模板路径")
	private String generatorPath;

	/** 模板描述 */
	@Schema(description = "模板描述")
	@ExcelProperty(value = "模板描述")
	private String templateDesc;

	/** 模板代码 */
	@Schema(description = "模板代码")
	@ExcelProperty(value = "模板代码")
	private String templateCode;

	/** 删除标记 */
	@Schema(description = "删除标记")
	private String delFlag;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setGeneratorPath(String generatorPath) {
		this.generatorPath = generatorPath;
	}

	public String getGeneratorPath() {
		return generatorPath;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}

	public String getTemplateDesc() {
		return templateDesc;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateCode() {
		return templateCode;
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
			.append("templateName", getTemplateName())
			.append("generatorPath", getGeneratorPath())
			.append("templateDesc", getTemplateDesc())
			.append("templateCode", getTemplateCode())
			.append("createTime", getCreateTime())
			.append("updateTime", getUpdateTime())
			.append("delFlag", getDelFlag())
			.append("createBy", getCreateBy())
			.append("updateBy", getUpdateBy())
			.toString();
	}

}
