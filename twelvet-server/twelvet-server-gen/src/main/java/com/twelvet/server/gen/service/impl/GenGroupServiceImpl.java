package com.twelvet.server.gen.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.twelvet.api.gen.domain.GenTemplate;
import com.twelvet.api.gen.domain.GenTemplateGroup;
import com.twelvet.api.gen.domain.dto.GenGroupDTO;
import com.twelvet.api.gen.domain.vo.GenGroupVO;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.server.gen.mapper.GenTemplateGroupMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.twelvet.framework.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.twelvet.server.gen.mapper.GenGroupMapper;
import com.twelvet.api.gen.domain.GenGroup;
import com.twelvet.server.gen.service.IGenGroupService;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	private GenTemplateGroupMapper genTemplateGroupMapper;

	/**
	 * 查询模板分组
	 * @param id 模板分组主键
	 * @return 模板分组
	 */
	@Override
	public GenGroupDTO selectGenGroupById(Long id) {
		GenGroupDTO genGroupDTO = new GenGroupDTO();
		GenGroup genGroup = genGroupMapper.selectGenGroupById(id);
		List<GenTemplateGroup> genTemplateGroupList = genTemplateGroupMapper.selectGenTemplateGroupListByGroupId(id);
		List<Long> templateIdList = genTemplateGroupList.stream().map(GenTemplateGroup::getTemplateId).toList();
		BeanUtils.copyProperties(genGroup, genGroupDTO);
		genGroupDTO.setTemplateIdList(templateIdList);
		return genGroupDTO;
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
	 * @param genGroupDTO 模板分组
	 * @return 结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertGenGroup(GenGroupDTO genGroupDTO) {
		GenGroup genGroup = new GenGroup();
		genGroup.setGroupName(genGroupDTO.getGroupName());
		genGroup.setGroupDesc(genGroupDTO.getGroupDesc());
		genGroup.setCreateTime(DateUtils.getNowDate());
		String loginUsername = SecurityUtils.getUsername();
		genGroup.setCreateBy(loginUsername);
		genGroup.setUpdateBy(loginUsername);
		int res = genGroupMapper.insertGenGroup(genGroup);

		List<Long> templateIdList = genGroupDTO.getTemplateIdList();
		for (Long templateId : templateIdList) {
			GenTemplateGroup genTemplateGroup = new GenTemplateGroup();
			genTemplateGroup.setGroupId(genGroup.getId());
			genTemplateGroup.setTemplateId(templateId);
			genTemplateGroupMapper.insertGenTemplateGroup(genTemplateGroup);
		}

		return res;
	}

	/**
	 * 修改模板分组
	 * @param genGroupDTO 模板分组
	 * @return 结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateGenGroup(GenGroupDTO genGroupDTO) {
		GenGroup genGroup = new GenGroup();
		Long id = genGroupDTO.getId();
		genGroup.setId(id);
		genGroup.setGroupName(genGroupDTO.getGroupName());
		genGroup.setGroupDesc(genGroupDTO.getGroupDesc());
		genGroup.setUpdateTime(DateUtils.getNowDate());
		String loginUsername = SecurityUtils.getUsername();
		genGroup.setCreateBy(loginUsername);
		genGroup.setUpdateBy(loginUsername);

		List<Long> templateIdList = genGroupDTO.getTemplateIdList();
		genTemplateGroupMapper.deleteGenTemplateGroupByGroupId(id);
		for (Long templateId : templateIdList) {
			GenTemplateGroup genTemplateGroup = new GenTemplateGroup();
			genTemplateGroup.setGroupId(id);
			genTemplateGroup.setTemplateId(templateId);
			genTemplateGroupMapper.insertGenTemplateGroup(genTemplateGroup);
		}

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
