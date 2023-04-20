package com.twelvet.server.system.service.impl;

import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.api.system.domain.dto.SysClientDetailsDTO;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import com.twelvet.server.system.mapper.SysClientDetailsMapper;
import com.twelvet.server.system.service.ISysClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 终端配置Service接口
 */
@Service
public class SysClientDetailsServiceImpl implements ISysClientDetailsService {

	@Autowired
	private SysClientDetailsMapper sysClientDetailsMapper;

	/**
	 * 查询终端配置
	 * @param clientId 终端配置ID
	 * @return 终端配置
	 */
	// @TwSynchronized("anyLock")
	@Override
	public SysClientDetails selectSysClientDetailsById(String clientId) {
		return sysClientDetailsMapper.selectSysClientDetailsById(clientId);
	}

	/**
	 * 查询终端配置列表
	 * @param sysClientDetailsDTO 终端配置
	 * @return 终端配置
	 */
	@Override
	public List<SysClientDetails> selectSysClientDetailsList(SysClientDetailsDTO sysClientDetailsDTO) {
		return sysClientDetailsMapper.selectSysClientDetailsList(sysClientDetailsDTO);
	}

	/**
	 * 新增终端配置
	 * @param sysClientDetails 终端配置
	 * @return 结果
	 */
	@Override
	public int insertSysClientDetails(SysClientDetails sysClientDetails) {
		return sysClientDetailsMapper.insertSysClientDetails(sysClientDetails);
	}

	/**
	 * 修改终端配置
	 * @param sysClientDetails 终端配置
	 * @return 结果
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#sysClientDetails.clientId")
	public int updateSysClientDetails(SysClientDetails sysClientDetails) {
		return sysClientDetailsMapper.updateSysClientDetails(sysClientDetails);
	}

	/**
	 * 批量删除终端配置
	 * @param clientIds 需要删除的终端配置ID
	 * @return 结果
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
	public int deleteSysClientDetailsByIds(String[] clientIds) {
		return sysClientDetailsMapper.deleteSysClientDetailsByIds(clientIds);
	}

}
