package com.twelvet.server.system;

import com.twelvet.framework.core.annotation.EnableTWTFeignClients;
import com.twelvet.framework.core.annotation.EnableTwelveTConfig;
import com.twelvet.framework.security.annotation.EnableTWTResourceServer;
import com.twelvet.framework.swagger.annotation.EnableTwelveTSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 启动程序
 */
@EnableTwelveTSwagger2
@EnableTWTResourceServer
@MapperScan("com.twelvet.**.mapper")
@EnableTwelveTConfig
@EnableTWTFeignClients
@SpringBootApplication
public class SystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

}
