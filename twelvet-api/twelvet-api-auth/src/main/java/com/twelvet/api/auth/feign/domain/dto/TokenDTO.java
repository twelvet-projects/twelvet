package com.twelvet.api.auth.feign.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 令牌管理DTO
 */
@Schema(description = "令牌管理DTO")
public class TokenDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 当前页
	 */
	@Schema(description = "当前页")
	private Integer current;

	/**
	 * 页数
	 */
	@Schema(description = "页数")
	private Integer pageSize;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "TokenDTO{" + "username='" + username + '\'' + ", current=" + current + ", pageSize=" + pageSize + '}';
	}

}
