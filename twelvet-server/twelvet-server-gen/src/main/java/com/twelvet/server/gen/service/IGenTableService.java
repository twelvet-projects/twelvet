package com.twelvet.server.gen.service;

import com.twelvet.api.gen.domain.GenTable;

import java.util.List;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 业务 服务层
 */
public interface IGenTableService {

	/**
	 * 查询业务列表
	 * @param genTable 业务信息
	 * @return 业务集合
	 */
	List<GenTable> selectGenTableList(GenTable genTable);

	/**
	 * 查询据库列表
	 * @param genTable 业务信息
	 * @return 数据库表集合
	 */
	List<GenTable> selectDbTableList(GenTable genTable);

	/**
	 * 查询据库列表
	 * @param tableNames 表名称组
	 * @return 数据库表集合
	 */
	List<GenTable> selectDbTableListByNames(String dsName, String[] tableNames);

	/**
	 * 查询所有表信息
	 * @return 表信息集合
	 */
	List<GenTable> selectGenTableAll();

	/**
	 * 查询业务信息
	 * @param id 业务ID
	 * @return 业务信息
	 */
	GenTable selectGenTableById(Long id);

	/**
	 * 修改业务
	 * @param genTable 业务信息
	 */
	void updateGenTable(GenTable genTable);

	/**
	 * 删除业务信息
	 * @param tableIds 需要删除的表数据ID
	 */
	void deleteGenTableByIds(Long[] tableIds);

	/**
	 * 导入表结构
	 * @param tableList 导入表列表
	 */
	void importGenTable(List<GenTable> tableList);

	/**
	 * 预览代码
	 * @param tableId 表编号
	 * @return 预览数据列表
	 */
	List<Map<String, String>> previewCode(Long tableId);

	/**
	 * 生成代码（下载方式）
	 * @param tableId 表ID
	 * @return 数据
	 */
	byte[] downloadCode(Long tableId);

	/**
	 * 生成代码（自定义路径）
	 * @param tableId 需要生成的表ID
	 */
	void generatorCode(Long tableId);

	/**
	 * 同步数据库
	 * @param tableId 表ID
	 */
	void synchDb(Long tableId);

	/**
	 * 批量生成代码（下载方式）
	 * @param tableIds 表ID数组
	 * @return 数据
	 */
	byte[] downloadCode(List<Long> tableIds);

	/**
	 * 修改保存参数校验
	 * @param genTable 业务信息
	 */
	void validateEdit(GenTable genTable);

}
