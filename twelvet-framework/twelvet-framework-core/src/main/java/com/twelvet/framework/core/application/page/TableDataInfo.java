package com.twelvet.framework.core.application.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 表格分页数据对象
 */
public class TableDataInfo<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 总记录数
	 */
	private long total;

	/**
	 * 列表数据
	 */
	private List<T> records;

	/**
	 * 分页
	 * @param list 列表数据
	 * @param total 总记录数
	 */
	public static <T> TableDataInfo<T> page(List<T> list, long total) {
		TableDataInfo<T> tableDataInfo = new TableDataInfo<>();
		tableDataInfo.setRecords(list);
		tableDataInfo.setTotal(total);
		return tableDataInfo;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

}
