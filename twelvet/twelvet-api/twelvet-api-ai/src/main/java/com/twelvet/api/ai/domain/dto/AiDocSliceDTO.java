package com.twelvet.api.ai.domain.dto;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "AI知识库文档分片对象DTO")
public class AiDocSliceDTO {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 分片ID
	 */
	@NotNull(message = "分片ID不允许为空")
	@Schema(description = "分片ID")
	private Long sliceId;

	/**
	 * 分片内容
	 */
	@NotBlank(message = "分片内容不允许为空")
	@Schema(description = "分片内容")
	private String content;

	public Long getSliceId() {
		return sliceId;
	}

	public void setSliceId(Long sliceId) {
		this.sliceId = sliceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("sliceId", getSliceId())
			.append("content", getContent())
			.toString();
	}

}
