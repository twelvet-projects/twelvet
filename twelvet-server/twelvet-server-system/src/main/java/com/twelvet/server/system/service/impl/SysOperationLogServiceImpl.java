package com.twelvet.server.system.service.impl;

import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.server.system.mapper.SysOperationLogMapper;
import com.twelvet.server.system.service.ISysOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 操作日志 服务层处理
 */
@Service
public class SysOperationLogServiceImpl implements ISysOperationLogService {

	@Autowired
	private SysOperationLogMapper sysOperationLogMapper;

	/**
	 * 新增操作日志
	 * @param operationLog 操作日志对象
	 * @return 结果
	 */
	@Override
	public int insertOperationLog(SysOperationLog operationLog) {
		return sysOperationLogMapper.insertOperationLog(operationLog);
	}

	/**
	 * 批量删除系统操作日志
	 * @param operationLogIds 需要删除的操作日志ID
	 * @return 结果
	 */
	@Override
	public int deleteOperationLogByIds(Long[] operationLogIds) {
		return sysOperationLogMapper.deleteOperationLogByIds(operationLogIds);
	}

	/**
	 * 清空操作日志
	 */
	@Override
	public void cleanOperationLog() {
		sysOperationLogMapper.cleanOperationLog();
	}

	/**
	 * 查询系统操作日志集合
	 * @param operationLog 操作日志对象
	 * @return 操作日志集合
	 */
	@Override
	public List<SysOperationLog> selectOperationLogList(SysOperationLog operationLog) {
		return sysOperationLogMapper.selectOperationLogList(operationLog);
	}

}
