package com.twelvet.server.system.mapper;

import com.twelvet.api.system.domain.SysOperationLog;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 操作日志 数据层
 */
public interface SysOperationLogMapper {

	/**
	 * 新增操作日志
	 * @param operationLog 操作日志对象
	 * @return 主键Id
	 */
	int insertOperationLog(SysOperationLog operationLog);

	/**
	 * 批量删除系统操作日志
	 * @param operationLog 需要删除的操作日志ID
	 * @return 结果
	 */
	int deleteOperationLogByIds(Long[] operationLog);

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
