package com.twelvet.framework.log;

import com.twelvet.api.system.feign.RemoteLogService;
import com.twelvet.framework.log.aspect.SysLogAspect;
import com.twelvet.framework.log.event.listener.SysLoginLogListener;
import com.twelvet.framework.log.event.listener.SysSysOperationLogEventLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 日志自动配置
 */
@EnableAsync
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

	/**
	 * 登录日志监听
	 * @param remoteLogService RemoteLogService
	 * @return SysLoginLogListener
	 */
	@Bean
	public SysLoginLogListener sysLoginLogListener(RemoteLogService remoteLogService) {
		return new SysLoginLogListener(remoteLogService);
	}

	/**
	 * 操作日志监听
	 * @param remoteLogService RemoteLogService
	 * @return SysLoginLogListener
	 */
	@Bean
	public SysSysOperationLogEventLogListener sysOperationLogEventLogListener(RemoteLogService remoteLogService) {
		return new SysSysOperationLogEventLogListener(remoteLogService);
	}

	/**
	 * 系统操作日志
	 * @return SysLogAspect
	 */
	@Bean
	public SysLogAspect sysLogAspect() {
		return new SysLogAspect();
	}

}
