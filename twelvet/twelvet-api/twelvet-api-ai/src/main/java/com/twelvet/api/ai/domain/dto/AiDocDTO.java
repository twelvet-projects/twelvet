package com.twelvet.api.ai.domain.dto;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.constant.RAGEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
	private String content;

	@Schema(description = "文件列表")
	private List<FileDTO> fileList;

	/**
	 * 文件上传
	 */
	public static class FileDTO implements Serializable {

		@Serial
		private static final long serialVersionUID = 1L;

		@Schema(description = "文件名称")
		private String fileName;

		@Schema(description = "文件地址")
		private String fileUrl;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileUrl() {
			return fileUrl;
		}

		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}

		@Override
		public String toString() {
			return "FileDTO{" + "fileName='" + fileName + '\'' + ", fileUrl='" + fileUrl + '\'' + '}';
		}

	}

	public @NotNull(message = "知识库不能为空") Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(@NotNull(message = "知识库不能为空") Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public @NotNull(message = "来源类型不能为空") RAGEnums.DocSourceTypeEnums getSourceType() {
		return sourceType;
	}

	public void setSourceType(@NotNull(message = "来源类型不能为空") RAGEnums.DocSourceTypeEnums sourceType) {
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

	public List<FileDTO> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileDTO> fileList) {
		this.fileList = fileList;
	}

	@Override
	public String toString() {
		return "AiDocDTO{" + "knowledgeId=" + knowledgeId + ", sourceType=" + sourceType + ", docName='" + docName
				+ '\'' + ", content='" + content + '\'' + ", fileList=" + fileList + '}';
	}

}
