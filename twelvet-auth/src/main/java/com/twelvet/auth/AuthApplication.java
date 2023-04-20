package com.twelvet.auth;

import com.twelvet.framework.core.annotation.EnableTWTFeignClients;
import com.twelvet.framework.core.annotation.EnableTwelveTConfig;
import com.twelvet.framework.swagger.annotation.EnableTwelveTSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 认证中心启动器
 */
@EnableTwelveTSwagger2
@EnableTwelveTConfig
@EnableTWTFeignClients
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
