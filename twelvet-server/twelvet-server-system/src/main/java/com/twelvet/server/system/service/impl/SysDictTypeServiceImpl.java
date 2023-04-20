package com.twelvet.server.system.service.impl;

import com.twelvet.api.system.domain.SysDictData;
import com.twelvet.api.system.domain.SysDictType;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.system.mapper.SysDictDataMapper;
import com.twelvet.server.system.mapper.SysDictTypeMapper;
import com.twelvet.server.system.service.ISysDictTypeService;
import com.twelvet.server.system.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 字典 业务层
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {

	@Autowired
	private SysDictTypeMapper dictTypeMapper;

	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * 根据条件分页查询字典类型
	 * @param dictType 字典类型信息
	 * @return 字典类型集合信息
	 */
	@Override
	public List<SysDictType> selectDictTypeList(SysDictType dictType) {
		return dictTypeMapper.selectDictTypeList(dictType);
	}

	/**
	 * 根据所有字典类型
	 * @return 字典类型集合信息
	 */
	@Override
	public List<SysDictType> selectDictTypeAll() {
		return dictTypeMapper.selectDictTypeAll();
	}

	/**
	 * 根据字典类型查询字典数据
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	@Override
	@Cacheable(value = CacheConstants.SYS_DICT_KEY, key = "#dictType", unless = "#result?.size() == 0")
	public List<SysDictData> selectDictDataByType(String dictType) {
		return dictDataMapper.selectDictDataByType(dictType);
	}

	/**
	 * 根据字典类型ID查询信息
	 * @param dictId 字典类型ID
	 * @return 字典类型
	 */
	@Override
	public SysDictType selectDictTypeById(Long dictId) {
		return dictTypeMapper.selectDictTypeById(dictId);
	}

	/**
	 * 根据字典类型查询信息
	 * @param dictType 字典类型
	 * @return 字典类型
	 */
	@Override
	public SysDictType selectDictTypeByType(String dictType) {
		return dictTypeMapper.selectDictTypeByType(dictType);
	}

	/**
	 * 批量删除字典类型信息
	 * @param dictIds 需要删除的字典ID
	 * @return 结果
	 */
	@Override
	public int deleteDictTypeByIds(Long[] dictIds) {
		for (Long dictId : dictIds) {
			SysDictType dictType = selectDictTypeById(dictId);
			if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
				throw new TWTException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
			}
		}
		int count = dictTypeMapper.deleteDictTypeByIds(dictIds);
		if (count > 0) {
			DictUtils.clearDictCache();
		}
		return count;
	}

	/**
	 * 清空缓存数据
	 */
	@Override
	public void clearCache() {
		DictUtils.clearDictCache();
	}

	/**
	 * 新增保存字典类型信息
	 * @param dictType 字典类型信息
	 * @return 结果
	 */
	@Override
	public int insertDictType(SysDictType dictType) {
		int row = dictTypeMapper.insertDictType(dictType);
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}

	/**
	 * 修改保存字典类型信息
	 * @param dictType 字典类型信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateDictType(SysDictType dictType) {
		SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
		dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
		int row = dictTypeMapper.updateDictType(dictType);
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}

	/**
	 * 校验字典类型称是否唯一
	 * @param dict 字典类型
	 * @return 结果
	 */
	@Override
	public String checkDictTypeUnique(SysDictType dict) {
		long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
		SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
		if (StringUtils.isNotNull(dictType) && dictType.getDictId() != dictId) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

}
