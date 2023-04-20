package com.twelvet.framework.log.event.listener;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.feign.RemoteLogService;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.log.event.SysLoginLogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 异步监听系统登录日志事件
 */
public class SysLoginLogListener {

	private final RemoteLogService remoteLogService;

	public SysLoginLogListener(RemoteLogService remoteLogService) {
		this.remoteLogService = remoteLogService;
	}

	@Async
	@Order
	@EventListener(SysLoginLogEvent.class)
	public void saveSysLog(SysLoginLogEvent event) {
		SysLoginInfo sysLoginInfo = (SysLoginInfo) event.getSource();
		remoteLogService.saveLoginInfo(sysLoginInfo);
	}

}
