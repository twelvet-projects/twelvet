package com.twelvet.api.ai.domain.dto;

import cn.idev.excel.annotation.ExcelProperty;
import jakarta.validation.constraints.NotNull;

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
	@NotNull(message = "文档ID不能为空")
	@Schema(description = "文档ID")
	private Long docId;

	/**
	 * 知识库ID
	 */
	@NotNull(message = "知识库ID不能为空")
	@Schema(description = "知识库ID")
	private Long knowledgeId;

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

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Long getKnowledgeId() {
		return knowledgeId;
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
		return "AiDocDTO{" + "docId=" + docId + ", knowledgeId=" + knowledgeId + ", docName='" + docName + '\'' + ", content='"
				+ content + '\'' + '}';
	}

}
