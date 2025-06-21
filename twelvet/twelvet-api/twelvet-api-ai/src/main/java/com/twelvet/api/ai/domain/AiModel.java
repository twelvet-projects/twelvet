package com.twelvet.api.ai.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.constant.ModelEnums;
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

	/** 供应商，枚举：ModelEnums.ModelProviderEnums */
	@Schema(description = "供应商，枚举：ModelEnums.ModelProviderEnums")
	@ExcelProperty(value = "供应商，枚举：ModelEnums.ModelProviderEnums")
	private ModelEnums.ModelProviderEnums modelSupplier;

	/** 模型 */
	@Schema(description = "模型")
	@ExcelProperty(value = "模型")
	private String model;

	/** 模型类型，枚举：ModelEnums.ModelTypeEnums */
	@Schema(description = "模型类型，枚举：ModelEnums.ModelTypeEnums")
	@ExcelProperty(value = "模型类型，枚举：ModelEnums.ModelTypeEnums")
	private ModelEnums.ModelTypeEnums modelType;

	/** 是否为同一类型模型的默认 */
	@Schema(description = "是否为同一类型模型的默认")
	@ExcelProperty(value = "是否为同一类型模型的默认")
	private Boolean defaultFlag;

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

	/** 创意活跃度 */
	@Schema(description = "创意活跃度")
	@ExcelProperty(value = "创意活跃度")
	private Double temperature;

	/** 思维开放度 */
	@Schema(description = "思维开放度")
	@ExcelProperty(value = "思维开放度")
	private Double topP;

	/** 特殊参数 */
	@Schema(description = "特殊参数")
	@ExcelProperty(value = "特殊参数")
	private String extParam;

	/** 是否删除 0：正常，1：删除 */
	@Schema(description = "是否删除 0：正常，1：删除")
	private Integer delFlag;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public ModelEnums.ModelProviderEnums getModelSupplier() {
		return modelSupplier;
	}

	public void setModelSupplier(ModelEnums.ModelProviderEnums modelSupplier) {
		this.modelSupplier = modelSupplier;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public ModelEnums.ModelTypeEnums getModelType() {
		return modelType;
	}

	public void setModelType(ModelEnums.ModelTypeEnums modelType) {
		this.modelType = modelType;
	}

	public Boolean getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(Boolean defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getTopP() {
		return topP;
	}

	public void setTopP(Double topP) {
		this.topP = topP;
	}

	public String getExtParam() {
		return extParam;
	}

	public void setExtParam(String extParam) {
		this.extParam = extParam;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("modelId", getModelId())
			.append("modelSupplier", getModelSupplier())
			.append("model", getModel())
			.append("modelType", getModelType())
			.append("defaultFlag", getDefaultFlag())
			.append("alias", getAlias())
			.append("apiKey", getApiKey())
			.append("baseUrl", getBaseUrl())
			.append("temperature", getTemperature())
			.append("topP", getTopP())
			.append("extParam", getExtParam())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
