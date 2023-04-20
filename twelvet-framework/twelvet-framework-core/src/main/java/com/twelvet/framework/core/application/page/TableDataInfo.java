package com.twelvet.framework.core.application.page;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 表格分页数据对象
 */
public class TableDataInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 总记录数
	 */
	private long total;

	/**
	 * 列表数据
	 */
	private List<?> records;

	/**
	 * 表格数据对象
	 */
	public TableDataInfo() {
	}

	/**
	 * 分页
	 * @param list 列表数据
	 * @param total 总记录数
	 */
	public TableDataInfo(List<?> list, int total) {
		this.records = list;
		this.total = total;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

}
