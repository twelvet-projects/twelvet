package com.twelvet.api.ai.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AI知识库对象 ai_model
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Schema(description = "AI知识库对象")
public class AiModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 知识库ID */
	@Schema(description = "知识库ID")
	private Long modelId;

	/** 知识库名称 */
	@Schema(description = "知识库名称")
	@ExcelProperty(value = "知识库名称")
	private String modelName;

	/** 欢迎语 */
	@Schema(description = "欢迎语")
	@ExcelProperty(value = "欢迎语")
	private String welcomeMsg;

	/** 上下文记忆会话数 */
	@Schema(description = "上下文记忆会话数")
	@ExcelProperty(value = "上下文记忆会话数")
	private Integer multiRound;

	/** 向量匹配条数 */
	@Schema(description = "向量匹配条数")
	@ExcelProperty(value = "向量匹配条数")
	private Integer topK;

	/** 知识库排序 */
	@Schema(description = "知识库排序")
	@ExcelProperty(value = "知识库排序")
	private Integer modelSort;

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	public String getWelcomeMsg() {
		return welcomeMsg;
	}

	public void setMultiRound(Integer multiRound) {
		this.multiRound = multiRound;
	}

	public Integer getMultiRound() {
		return multiRound;
	}

	public void setTopK(Integer topK) {
		this.topK = topK;
	}

	public Integer getTopK() {
		return topK;
	}

	public void setModelSort(Integer modelSort) {
		this.modelSort = modelSort;
	}

	public Integer getModelSort() {
		return modelSort;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("modelId", getModelId())
			.append("modelName", getModelName())
			.append("welcomeMsg", getWelcomeMsg())
			.append("multiRound", getMultiRound())
			.append("topK", getTopK())
			.append("modelSort", getModelSort())
			.toString();
	}

}
