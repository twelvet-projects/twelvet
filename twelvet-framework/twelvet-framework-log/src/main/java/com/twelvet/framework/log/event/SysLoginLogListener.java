package com.twelvet.framework.log.event;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.feign.RemoteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 异步监听系统登录日志事件
 */
public class SysLoginLogListener {

	@Autowired
	private RemoteLogService remoteLogService;

	@Async
	@Order
	@EventListener(SysLoginLogEvent.class)
	public void saveSysLog(SysLoginLogEvent event) {
		SysLoginInfo sysLog = (SysLoginInfo) event.getSource();
		remoteLogService.saveLoginInfo(sysLog);
	}

}