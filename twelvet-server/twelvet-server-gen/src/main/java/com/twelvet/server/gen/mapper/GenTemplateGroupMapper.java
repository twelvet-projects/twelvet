package com.twelvet.server.gen.mapper;

import com.twelvet.api.gen.domain.GenTemplateGroup;

import java.util.List;

/**
 * 模板分组关联Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface GenTemplateGroupMapper {

	/**
	 * 查询模板分组关联
	 * @param groupId 模板分组关联主键
	 * @return 模板分组关联
	 */
	public GenTemplateGroup selectGenTemplateGroupByGroupId(Long groupId);

	/**
	 * 查询模板分组关联列表
	 * @param groupId 模板分组关联ID
	 * @return 模板分组关联集合
	 */
	public List<GenTemplateGroup> selectGenTemplateGroupListByGroupId(Long groupId);

	/**
	 * 新增模板分组关联
	 * @param genTemplateGroup 模板分组关联
	 * @return 结果
	 */
	public int insertGenTemplateGroup(GenTemplateGroup genTemplateGroup);

	/**
	 * 修改模板分组关联
	 * @param genTemplateGroup 模板分组关联
	 * @return 结果
	 */
	public int updateGenTemplateGroup(GenTemplateGroup genTemplateGroup);

	/**
	 * 删除模板分组关联
	 * @param groupId 模板分组关联主键
	 * @return 结果
	 */
	public int deleteGenTemplateGroupByGroupId(Long groupId);

	/**
	 * 批量删除模板分组关联
	 * @param groupIds 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteGenTemplateGroupByGroupIds(Long[] groupIds);

}
