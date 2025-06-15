package com.twelvet.api.ai.domain.vo;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * AI大模型对象VO ai_model
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-20
 */
@Schema(description = "AI大模型对象VO")
public class AiModelVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/** 模型ID */
	@Schema(description = "模型ID")
	private Long modelId;

	/** 供应商，枚举：ModelEnums.ModelProviderEnums */
	@Schema(description = "供应商，枚举：ModelEnums.ModelProviderEnums")
	@ExcelProperty(value = "供应商，枚举：ModelEnums.ModelProviderEnums")
	private String modelProviderName;

	/** 模型 */
	@Schema(description = "模型")
	@ExcelProperty(value = "模型")
	private String model;

	/** 模型类型，枚举：ModelEnums.ModelTypeEnums */
	@Schema(description = "模型类型，枚举：ModelEnums.ModelTypeEnums")
	@ExcelProperty(value = "模型类型，枚举：ModelEnums.ModelTypeEnums")
	private String modelTypeName;

	/** 别名 */
	@Schema(description = "别名")
	@ExcelProperty(value = "别名")
	private String alias;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelProviderName() {
		return modelProviderName;
	}

	public void setModelProviderName(String modelProviderName) {
		this.modelProviderName = modelProviderName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelTypeName() {
		return modelTypeName;
	}

	public void setModelTypeName(String modelTypeName) {
		this.modelTypeName = modelTypeName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "AiModelVO{" + "modelId=" + modelId + ", modelProviderName='" + modelProviderName + '\'' + ", model='"
				+ model + '\'' + ", modelTypeName='" + modelTypeName + '\'' + ", alias='" + alias + '\'' + '}';
	}

}
