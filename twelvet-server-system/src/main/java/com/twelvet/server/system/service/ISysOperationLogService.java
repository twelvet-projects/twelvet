package com.twelvet.server.system.service;

import com.twelvet.api.system.domain.SysOperationLog;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 操作日志 服务层
 */
public interface ISysOperationLogService {

	/**
	 * 新增操作日志
	 * @param operationLog 操作日志对象
	 * @return 结果
	 */
	int insertOperationLog(SysOperationLog operationLog);

	/**
	 * 批量删除系统操作日志
	 * @param operIds 需要删除的操作日志ID
	 * @return 结果
	 */
	int deleteOperationLogByIds(Long[] operIds);

	/**
	 * 清空操作日志
	 */
	void cleanOperationLog();

	/**
	 * 查询系统操作日志集合
	 * @param operLog 操作日志对象
	 * @return 操作日志集合
	 */
	List<SysOperationLog> selectOperationLogList(SysOperationLog operLog);

}
