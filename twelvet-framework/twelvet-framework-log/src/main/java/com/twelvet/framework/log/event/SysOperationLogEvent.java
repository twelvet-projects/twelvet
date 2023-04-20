package com.twelvet.framework.log.event;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 系统操作日志事件
 */
public class SysOperationLogEvent extends ApplicationEvent {

	public SysOperationLogEvent(SysOperationLog source) {
		super(source);
	}

}
