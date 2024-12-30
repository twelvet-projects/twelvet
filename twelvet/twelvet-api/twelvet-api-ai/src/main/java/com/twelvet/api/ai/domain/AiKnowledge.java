package com.twelvet.api.ai.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * AI知识库对象 ai_knowledge
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Schema(description = "AI知识库对象")
public class AiKnowledge extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 知识库ID
	 */
	@Schema(description = "知识库ID")
	private Long knowledgeId;

	/**
	 * 知识库名称
	 */
	@Schema(description = "知识库名称")
	@ExcelProperty(value = "知识库名称")
	private String knowledgeName;

	/**
	 * 欢迎语
	 */
	@Schema(description = "欢迎语")
	@ExcelProperty(value = "欢迎语")
	private String welcomeMsg;

	/**
	 * 上下文记忆会话数
	 */
	@Schema(description = "上下文记忆会话数")
	@ExcelProperty(value = "上下文记忆会话数")
	private Integer multiRound;

	/**
	 * 向量匹配条数
	 */
	@Schema(description = "向量匹配条数")
	@ExcelProperty(value = "向量匹配条数")
	private Integer topK;

	/**
	 * 知识库排序
	 */
	@Schema(description = "知识库排序")
	@ExcelProperty(value = "知识库排序")
	private Integer knowledgeSort;

	/**
	 * 是否删除 0：正常，0：删除
	 */
	@Schema(description = "是否删除 0：正常，0：删除")
	@ExcelProperty(value = "是否删除 0：正常，0：删除")
	private Boolean delFlag;

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	public String getKnowledgeName() {
		return knowledgeName;
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

	public void setKnowledgeSort(Integer knowledgeSort) {
		this.knowledgeSort = knowledgeSort;
	}

	public Integer getKnowledgeSort() {
		return knowledgeSort;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("knowledgeId", getKnowledgeId())
			.append("knowledgeName", getKnowledgeName())
			.append("welcomeMsg", getWelcomeMsg())
			.append("multiRound", getMultiRound())
			.append("topK", getTopK())
			.append("knowledgeSort", getKnowledgeSort())
			.append("delFlag", getDelFlag())
			.toString();
	}

}