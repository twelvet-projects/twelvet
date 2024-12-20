package com.twelvet.api.ai.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AI大模型对象 ai_model
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-20
 */
@Schema(description = "AI大模型对象")
public class AiModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 模型ID */
	@Schema(description = "模型ID")
	private Long modelId;

	/** 供应商，枚举：ModelEnums.ModelSupplierEnums */
	@Schema(description = "供应商，枚举：ModelEnums.ModelSupplierEnums")
	@ExcelProperty(value = "供应商，枚举：ModelEnums.ModelSupplierEnums")
	private String modelSupplier;

	/** 模型 */
	@Schema(description = "模型")
	@ExcelProperty(value = "模型")
	private String model;

	/** 模型类型，枚举：ModelEnums.ModelTypeEnums */
	@Schema(description = "模型类型，枚举：ModelEnums.ModelTypeEnums")
	@ExcelProperty(value = "模型类型，枚举：ModelEnums.ModelTypeEnums")
	private String modelType;

	/** 别名 */
	@Schema(description = "别名")
	@ExcelProperty(value = "别名")
	private String alias;

	/** apiKey */
	@Schema(description = "apiKey")
	@ExcelProperty(value = "apiKey")
	private String apiKey;

	/** 模型请求地址 */
	@Schema(description = "模型请求地址")
	@ExcelProperty(value = "模型请求地址")
	private String baseUrl;

	/** 是否删除 0：正常，0：删除 */
	@Schema(description = "是否删除 0：正常，0：删除")
	private Integer delFlag;

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelSupplier(String modelSupplier) {
		this.modelSupplier = modelSupplier;
	}

	public String getModelSupplier() {
		return modelSupplier;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getModelType() {
		return modelType;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("modelId", getModelId())
			.append("modelSupplier", getModelSupplier())
			.append("model", getModel())
			.append("modelType", getModelType())
			.append("alias", getAlias())
			.append("apiKey", getApiKey())
			.append("baseUrl", getBaseUrl())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
