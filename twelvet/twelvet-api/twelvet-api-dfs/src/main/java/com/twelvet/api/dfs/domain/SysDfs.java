package com.twelvet.api.dfs.domain;

import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;

@Schema(description = "SysDfs")
public class SysDfs extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long fileId;

	/**
	 * 文件路径
	 */
	@Schema(description = "文件路径")
	private String path;

	/**
	 * 文件名称
	 */
	@Schema(description = "文件名称")
	private String fileName;

	/**
	 * 文件原名称
	 */
	@Schema(description = "文件原名称")
	private String originalFileName;

	/**
	 * 文件分组
	 */
	@Schema(description = "文件分组")
	private String spaceName;

	/**
	 * 文件大小
	 */
	@Schema(description = "文件大小")
	private Long size;

	/**
	 * 文件类型
	 */
	@Schema(description = "文件类型")
	private String type;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("fileId", getFileId())
			.append("fileName", getFileName())
			.append("path", getPath())
			.append("spaceName", getSpaceName())
			.append("originalName", getOriginalFileName())
			.append("size", getSize())
			.append("type", getType())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.toString();
	}

}
