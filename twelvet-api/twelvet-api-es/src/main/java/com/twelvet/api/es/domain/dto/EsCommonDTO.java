package com.twelvet.api.es.domain.dto;

import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 公共搜素VO
 */
public class EsCommonDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private String query;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 页数
	 */
	private int page;

	/**
	 * 每页数量
	 */
	private int pageSize;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "EsCommonDTO{" + "query='" + query + '\'' + ", status=" + status + ", page=" + page + ", pageSize="
				+ pageSize + '}';
	}

}
