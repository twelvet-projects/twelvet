package com.twelvet.server.gen.service;

import java.util.List;

import com.twelvet.api.gen.domain.GenTemplate;

/**
 * 代码生成业务模板Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface IGenTemplateService {

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
	 * 批量删除代码生成业务模板
	 * @param ids 需要删除的代码生成业务模板主键集合
	 * @return 结果
	 */
	public int deleteGenTemplateByIds(Long[] ids);

	/**
	 * 删除代码生成业务模板信息
	 * @param id 代码生成业务模板主键
	 * @return 结果
	 */
	public int deleteGenTemplateById(Long id);

}
