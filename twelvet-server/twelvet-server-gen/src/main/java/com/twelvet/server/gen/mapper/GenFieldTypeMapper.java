package com.twelvet.server.gen.mapper;

import java.util.List;

import com.twelvet.api.gen.domain.GenFieldType;

/**
 * 字段类型管理Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface GenFieldTypeMapper {

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
	 * 查询字段类型管理所有列表
	 * @return 字段类型管理集合
	 */
	public List<GenFieldType> selectGenFieldTypeListAll();

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
	 * 删除字段类型管理
	 * @param id 字段类型管理主键
	 * @return 结果
	 */
	public int deleteGenFieldTypeById(Long id);

	/**
	 * 批量删除字段类型管理
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteGenFieldTypeByIds(Long[] ids);

}
