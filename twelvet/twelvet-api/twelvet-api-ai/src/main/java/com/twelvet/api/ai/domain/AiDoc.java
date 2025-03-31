package com.twelvet.api.ai.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * AI知识库文档对象 ai_doc
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Schema(description = "AI知识库文档对象")
public class AiDoc extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 文档ID
	 */
	@Schema(description = "文档ID")
	private Long docId;

	/**
	 * 知识库ID
	 */
	@Schema(description = "知识库ID")
	@ExcelProperty(value = "知识库ID")
	private Long knowledgeId;

	/**
	 * 来源类型
	 */
	@Schema(description = "来源类型")
	@ExcelProperty(value = "来源类型")
	private RAGEnums.DocSourceTypeEnums sourceType;

	/**
	 * 文档名称
	 */
	@Schema(description = "文档名称")
	@ExcelProperty(value = "文档名称")
	private String docName;

	/**
	 * 是否删除 0：正常，1：删除
	 */
	@Schema(description = "是否删除 0：正常，1：删除")
	@ExcelProperty(value = "是否删除 0：正常，1：删除")
	private Boolean delFlag;

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public RAGEnums.DocSourceTypeEnums getSourceType() {
		return sourceType;
	}

	public void setSourceType(RAGEnums.DocSourceTypeEnums sourceType) {
		this.sourceType = sourceType;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("docId", getDocId())
			.append("knowledgeId", getKnowledgeId())
			.append("sourceType", getSourceType())
			.append("docName", getDocName())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
