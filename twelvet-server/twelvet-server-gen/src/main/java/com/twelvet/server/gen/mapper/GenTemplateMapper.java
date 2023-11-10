package com.twelvet.server.gen.mapper;

import java.util.List;

import com.twelvet.api.gen.domain.GenTemplate;

/**
 * 代码生成业务模板Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface GenTemplateMapper {

	/**
	 * 查询代码生成业务模板
	 * @param id 代码生成业务模板主键
	 * @return 代码生成业务模板
	 */
	public GenTemplate selectGenTemplateById(Long id);

	/**
	 * 查询代码生成业务模板列表
	 * @param genTemplate 代码生成业务模板
	 * @return 代码生成业务模板集合
	 */
	public List<GenTemplate> selectGenTemplateList(GenTemplate genTemplate);

	/**
	 * 新增代码生成业务模板
	 * @param genTemplate 代码生成业务模板
	 * @return 结果
	 */
	public int insertGenTemplate(GenTemplate genTemplate);

	/**
	 * 修改代码生成业务模板
	 * @param genTemplate 代码生成业务模板
	 * @return 结果
	 */
	public int updateGenTemplate(GenTemplate genTemplate);

	/**
	 * 删除代码生成业务模板
	 * @param id 代码生成业务模板主键
	 * @return 结果
	 */
	public int deleteGenTemplateById(Long id);

	/**
	 * 批量删除代码生成业务模板
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteGenTemplateByIds(Long[] ids);


	/**
	 * 根据分组ID查询代码生成业务模板列表
	 * @param groupId 分组ID
	 * @return 代码生成业务模板集合
	 */
	public List<GenTemplate> selectGenTemplateListByGroupId(Long groupId);
}
