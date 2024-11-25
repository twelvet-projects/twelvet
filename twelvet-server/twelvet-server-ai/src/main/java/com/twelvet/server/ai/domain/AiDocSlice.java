package com.twelvet.server.ai.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * AI知识库文档分片对象 ai_doc_slice
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Schema(description = "AI知识库文档分片对象")
public class AiDocSlice extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 分片ID
	 */
	@Schema(description = "分片ID")
	private Long sliceId;

	/**
	 * 知识库ID
	 */
	@Schema(description = "知识库ID")
	@ExcelProperty(value = "知识库ID")
	private Long modelId;

	/**
	 * 文档ID
	 */
	@Schema(description = "文档ID")
	@ExcelProperty(value = "文档ID")
	private Long docId;

	/**
	 * 分片名称
	 */
	@Schema(description = "分片名称")
	@ExcelProperty(value = "分片名称")
	private String sliceName;

	/**
	 * 分片内容
	 */
	@Schema(description = "分片内容")
	@ExcelProperty(value = "分片内容")
	private String content;

	/**
	 * 是否删除 0：正常，0：删除
	 */
	@Schema(description = "是否删除 0：正常，0：删除")
	@ExcelProperty(value = "是否删除 0：正常，0：删除")
	private Boolean delFlag;

	public void setSliceId(Long sliceId) {
		this.sliceId = sliceId;
	}

	public Long getSliceId() {
		return sliceId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Long getDocId() {
		return docId;
	}

	public void setSliceName(String sliceName) {
		this.sliceName = sliceName;
	}

	public String getSliceName() {
		return sliceName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("sliceId", getSliceId())
			.append("modelId", getModelId())
			.append("docId", getDocId())
			.append("sliceName", getSliceName())
			.append("content", getContent())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
