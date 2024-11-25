package com.twelvet.server.gen.mapper;

import com.twelvet.api.gen.domain.GenTable;
import com.twelvet.api.gen.domain.GenTableColumn;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 代码生成 数据层接口
 */
public interface GenMapper {

	/**
	 * 根据表名称查询列信息
	 * @param tableName 表名称
	 * @return 列信息
	 */
	List<GenTableColumn> selectDbTableColumnsByName(String tableName);

	/**
	 * 查询据库列表
	 * @param tableNames 表名称组
	 * @return 数据库表集合
	 */
	List<GenTable> selectDbTableListByNames(String[] tableNames);

	/**
	 * 查询据库列表
	 * @param genTable 业务信息
	 * @return 数据库表集合
	 */
	List<GenTable> selectDbTableList(GenTable genTable);

}
