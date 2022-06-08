package com.twelvet.api.es.domain.vo;

import com.twelvet.api.es.domain.EsCommon;

import java.io.Serializable;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 公共搜素VO
 */
public class EsCommonVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private long total;

	/**
	 * 内容信息
	 */
	private List<EsCommon> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<EsCommon> getRows() {
		return rows;
	}

	public void setRows(List<EsCommon> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "EsCommonVO{" + "total=" + total + ", rows='" + rows + '\'' + '}';
	}

}
