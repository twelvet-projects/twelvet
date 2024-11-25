package com.twelvet.server.ai.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * AI知识库文档对象 ai_doc
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Schema(description = "AI知识库文档DTO")
public class AiDocDTO implements Serializable {

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
	private Long modelId;

	/**
	 * 文档名称
	 */
	@Schema(description = "文档名称")
	private String docName;

	/** 内容 */
	@Schema(description = "内容")
	@ExcelProperty(value = "内容")
	private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "AiDocDTO{" + "docId=" + docId + ", modelId=" + modelId + ", docName='" + docName + '\'' + ", content='"
				+ content + '\'' + '}';
	}

}
