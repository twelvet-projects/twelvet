package com.twelvet.server.gen.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import com.twelvet.framework.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.twelvet.framework.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.twelvet.server.gen.mapper.GenTemplateMapper;
import com.twelvet.api.gen.domain.GenTemplate;
import com.twelvet.server.gen.service.IGenTemplateService;

/**
 * 代码生成业务模板Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Service
public class GenTemplateServiceImpl implements IGenTemplateService {

	@Autowired
	private GenTemplateMapper genTemplateMapper;

	/**
	 * 查询代码生成业务模板
	 * @param id 代码生成业务模板主键
	 * @return 代码生成业务模板
	 */
	@Override
	public GenTemplate selectGenTemplateById(Long id) {
		return genTemplateMapper.selectGenTemplateById(id);
	}

	/**
	 * 查询代码生成业务模板列表
	 * @param genTemplate 代码生成业务模板
	 * @return 代码生成业务模板
	 */
	@Override
	public List<GenTemplate> selectGenTemplateList(GenTemplate genTemplate) {
		return genTemplateMapper.selectGenTemplateList(genTemplate);
	}

	/**
	 * 新增代码生成业务模板
	 * @param genTemplate 代码生成业务模板
	 * @return 结果
	 */
	@Override
	public int insertGenTemplate(GenTemplate genTemplate) {
		genTemplate.setCreateTime(LocalDateTime.now());
		String loginUsername = SecurityUtils.getUsername();
		genTemplate.setCreateBy(loginUsername);
		genTemplate.setUpdateBy(loginUsername);
		return genTemplateMapper.insertGenTemplate(genTemplate);
	}

	/**
	 * 修改代码生成业务模板
	 * @param genTemplate 代码生成业务模板
	 * @return 结果
	 */
	@Override
	public int updateGenTemplate(GenTemplate genTemplate) {
		genTemplate.setUpdateTime(LocalDateTime.now());
		String loginUsername = SecurityUtils.getUsername();
		genTemplate.setCreateBy(loginUsername);
		genTemplate.setUpdateBy(loginUsername);
		return genTemplateMapper.updateGenTemplate(genTemplate);
	}

	/**
	 * 批量删除代码生成业务模板
	 * @param ids 需要删除的代码生成业务模板主键
	 * @return 结果
	 */
	@Override
	public int deleteGenTemplateByIds(Long[] ids) {
		return genTemplateMapper.deleteGenTemplateByIds(ids);
	}

	/**
	 * 删除代码生成业务模板信息
	 * @param id 代码生成业务模板主键
	 * @return 结果
	 */
	@Override
	public int deleteGenTemplateById(Long id) {
		return genTemplateMapper.deleteGenTemplateById(id);
	}

}
