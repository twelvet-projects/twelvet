package com.twelvet.server.gen.mapper;

import java.util.List;

import com.twelvet.api.gen.domain.GenGroup;

/**
 * 模板分组Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface GenGroupMapper {

	/**
	 * 查询模板分组
	 * @param id 模板分组主键
	 * @return 模板分组
	 */
	public GenGroup selectGenGroupById(Long id);

	/**
	 * 查询模板分组列表
	 * @param genGroup 模板分组
	 * @return 模板分组集合
	 */
	public List<GenGroup> selectGenGroupList(GenGroup genGroup);

	/**
	 * 新增模板分组
	 * @param genGroup 模板分组
	 * @return 结果
	 */
	public int insertGenGroup(GenGroup genGroup);

	/**
	 * 修改模板分组
	 * @param genGroup 模板分组
	 * @return 结果
	 */
	public int updateGenGroup(GenGroup genGroup);

	/**
	 * 删除模板分组
	 * @param id 模板分组主键
	 * @return 结果
	 */
	public int deleteGenGroupById(Long id);

	/**
	 * 批量删除模板分组
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteGenGroupByIds(Long[] ids);

}
