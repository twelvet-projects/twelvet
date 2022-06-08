package com.twelvet.api.es.domain;

import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 公共搜索
 */
public class EsCommon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容信息
	 */
	private String info;

	/**
	 * 状态
	 */
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "EsCommon{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", info='" + info + '\'' + ", status="
				+ status + '}';
	}

}
