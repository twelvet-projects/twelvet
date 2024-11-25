package com.twelvet.api.dfs.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 文件信息
 */
@Schema(description = "文件信息")
public class SysFile implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 文件名称
	 */
	@Schema(description = "文件名称")
	private String name;

	/**
	 * 文件地址
	 */
	@Schema(description = "文件地址")
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", getName())
			.append("url", getUrl())
			.toString();
	}

}
