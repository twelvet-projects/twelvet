package com.twelvet.server.ai.domain;

import com.alibaba.excel.annotation.ExcelProperty;
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
	private Long modelId;

	/**
	 * 文档名称
	 */
	@Schema(description = "文档名称")
	@ExcelProperty(value = "文档名称")
	private String docName;

	/**
	 * 是否删除 0：正常，0：删除
	 */
	@Schema(description = "是否删除 0：正常，0：删除")
	@ExcelProperty(value = "是否删除 0：正常，0：删除")
	private Boolean delFlag;

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Long getDocId() {
		return docId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocName() {
		return docName;
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
			.append("modelId", getModelId())
			.append("docName", getDocName())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
