package com.twelvet.server.gen.mapper;

import java.util.List;

import com.twelvet.api.gen.domain.GenTemplateGroup;

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
	 * @param genTemplateGroup 模板分组关联
	 * @return 模板分组关联集合
	 */
	public List<GenTemplateGroup> selectGenTemplateGroupList(GenTemplateGroup genTemplateGroup);

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
