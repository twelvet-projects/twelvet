package com.twelvet.server.gen.service;

import com.twelvet.api.gen.domain.GenFieldType;

import java.util.List;

/**
 * 字段类型管理Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface IGenFieldTypeService {

	/**
	 * 查询字段类型管理
	 * @param id 字段类型管理主键
	 * @return 字段类型管理
	 */
	public GenFieldType selectGenFieldTypeById(Long id);

	/**
	 * 查询字段类型管理列表
	 * @param genFieldType 字段类型管理
	 * @return 字段类型管理集合
	 */
	public List<GenFieldType> selectGenFieldTypeList(GenFieldType genFieldType);

	/**
	 * 新增字段类型管理
	 * @param genFieldType 字段类型管理
	 * @return 结果
	 */
	public int insertGenFieldType(GenFieldType genFieldType);

	/**
	 * 修改字段类型管理
	 * @param genFieldType 字段类型管理
	 * @return 结果
	 */
	public int updateGenFieldType(GenFieldType genFieldType);

	/**
	 * 批量删除字段类型管理
	 * @param ids 需要删除的字段类型管理主键集合
	 * @return 结果
	 */
	public int deleteGenFieldTypeByIds(Long[] ids);

	/**
	 * 删除字段类型管理信息
	 * @param id 字段类型管理主键
	 * @return 结果
	 */
	public int deleteGenFieldTypeById(Long id);

}
