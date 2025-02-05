package com.alibaba.nacos;

import com.alibaba.nacos.console.config.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: nacos console 源码运行，方便开发 生产从官网下载zip最新版集群配置运行
 * https://github.com/alibaba/nacos/releases
 */
@EnableScheduling
@SpringBootApplication
public class NacosApplication {

	public static void main(String[] args) {
		if (initEnv()) {
			SpringApplication.run(NacosApplication.class, args);
		}
	}

	/**
	 * 初始化运行环境
	 */
	private static boolean initEnv() {
		System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
		System.setProperty(ConfigConstants.AUTH_ENABLED, "true");
		System.setProperty(ConfigConstants.LOG_BASEDIR, "logs");
		System.setProperty(ConfigConstants.LOG_ENABLED, "false");
		System.setProperty(ConfigConstants.NACOS_CONTEXT_PATH, "/nacos");
		return true;
	}

}
