package com.twelvet.api.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 用户和岗位关联 sys_user_post
 */
@Schema(description = "用户和岗位关联")
public class SysUserPost implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@Schema(description = "用户ID")
	private Long userId;

	/** 岗位ID */
	@Schema(description = "岗位ID")
	private Long postId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
			.append("postId", getPostId())
			.toString();
	}

}
