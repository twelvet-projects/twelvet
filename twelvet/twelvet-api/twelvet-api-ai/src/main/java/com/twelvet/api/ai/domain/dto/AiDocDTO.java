package com.twelvet.api.ai.domain.dto;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.constant.RAGEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

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
	 * 知识库ID
	 */
	@NotNull(message = "知识库不能为空")
	@Schema(description = "知识库")
	private Long knowledgeId;

	/**
	 * 来源类型
	 */
	@NotNull(message = "来源类型不能为空")
	@Schema(description = "来源类型")
	private RAGEnums.DocSourceTypeEnums sourceType;

	/**
	 * 文档名称
	 */
	@Schema(description = "文档名称")
	private String docName;

	/** 内容 */
	@Schema(description = "内容")
	@ExcelProperty(value = "内容")
	private String content;

	public @NotNull(message = "知识库ID不能为空") Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(@NotNull(message = "知识库ID不能为空") Long knowledgeId) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "AiDocDTO{" + ", knowledgeId=" + knowledgeId + ", sourceType=" + sourceType + ", docName='" + docName
				+ '\'' + ", content='" + content + '\'' + '}';
	}

}
