package com.twelvet.server.mq.service;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;

/**
 * @author twelvet
 * <p>
 * 系统操作登录日志业务层
 */
public interface SysOperationLogService {

	/**
	 * 发送系统登录日志MQ
	 * @param sysOperationLog SysLoginInfo
	 */
	void sendSysOperationLog(SysOperationLog sysOperationLog);

}
