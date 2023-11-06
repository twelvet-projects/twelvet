package com.twelvet.server.gen.service.impl;

import java.util.List;
import com.twelvet.framework.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.twelvet.framework.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.twelvet.server.gen.mapper.GenGroupMapper;
import com.twelvet.api.gen.domain.GenGroup;
import com.twelvet.server.gen.service.IGenGroupService;

/**
 * 模板分组Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Service
public class GenGroupServiceImpl implements IGenGroupService {

	@Autowired
	private GenGroupMapper genGroupMapper;

	/**
	 * 查询模板分组
	 * @param id 模板分组主键
	 * @return 模板分组
	 */
	@Override
	public GenGroup selectGenGroupById(Long id) {
		return genGroupMapper.selectGenGroupById(id);
	}

	/**
	 * 查询模板分组列表
	 * @param genGroup 模板分组
	 * @return 模板分组
	 */
	@Override
	public List<GenGroup> selectGenGroupList(GenGroup genGroup) {
		return genGroupMapper.selectGenGroupList(genGroup);
	}

	/**
	 * 新增模板分组
	 * @param genGroup 模板分组
	 * @return 结果
	 */
	@Override
	public int insertGenGroup(GenGroup genGroup) {
		genGroup.setCreateTime(DateUtils.getNowDate());
		String loginUsername = SecurityUtils.getUsername();
		genGroup.setCreateBy(loginUsername);
		genGroup.setUpdateBy(loginUsername);
		return genGroupMapper.insertGenGroup(genGroup);
	}

	/**
	 * 修改模板分组
	 * @param genGroup 模板分组
	 * @return 结果
	 */
	@Override
	public int updateGenGroup(GenGroup genGroup) {
		genGroup.setUpdateTime(DateUtils.getNowDate());
		String loginUsername = SecurityUtils.getUsername();
		genGroup.setCreateBy(loginUsername);
		genGroup.setUpdateBy(loginUsername);
		return genGroupMapper.updateGenGroup(genGroup);
	}

	/**
	 * 批量删除模板分组
	 * @param ids 需要删除的模板分组主键
	 * @return 结果
	 */
	@Override
	public int deleteGenGroupByIds(Long[] ids) {
		return genGroupMapper.deleteGenGroupByIds(ids);
	}

	/**
	 * 删除模板分组信息
	 * @param id 模板分组主键
	 * @return 结果
	 */
	@Override
	public int deleteGenGroupById(Long id) {
		return genGroupMapper.deleteGenGroupById(id);
	}

}
