package com.twelvet.server.system.mapper;

import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.api.system.domain.dto.SysClientDetailsDTO;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: Oauth2 数据层
 */
public interface SysClientDetailsMapper {

	/**
	 * 查询终端配置
	 * @param clientId 终端配置ID
	 * @return 终端配置
	 */
	SysClientDetails selectSysClientDetailsById(String clientId);

	/**
	 * 查询终端配置列表
	 * @param sysClientDetailsDTO 终端配置
	 * @return 终端配置集合
	 */
	List<SysClientDetails> selectSysClientDetailsList(SysClientDetailsDTO sysClientDetailsDTO);

	/**
	 * 新增终端配置
	 * @param sysClientDetails 终端配置
	 * @return 结果
	 */
	int insertSysClientDetails(SysClientDetails sysClientDetails);

	/**
	 * 修改终端配置
	 * @param sysClientDetails 终端配置
	 * @return 结果
	 */
	int updateSysClientDetails(SysClientDetails sysClientDetails);

	/**
	 * 删除终端配置
	 * @param clientId 终端配置ID
	 * @return 结果
	 */
	int deleteSysClientDetailsById(String clientId);

	/**
	 * 批量删除终端配置
	 * @param clientIds 需要删除的数据ID
	 * @return 结果
	 */
	int deleteSysClientDetailsByIds(String[] clientIds);

}
