package com.alibaba.nacos.config;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 、覆盖nacos 默认配置
 */
public interface ConfigConstants {

	/**
	 * The System property name of Standalone mode
	 */
	String STANDALONE_MODE = "nacos.standalone";

	/**
	 * 是否开启认证
	 */
	String AUTH_ENABLED = "nacos.core.auth.enabled";

	/**
	 * 日志目录
	 */
	String LOG_BASEDIR = "server.tomcat.basedir";

	/**
	 * access_log日志开关
	 */
	String LOG_ENABLED = "server.tomcat.accesslog.enabled";

}
