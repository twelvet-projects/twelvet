package com.twelvet.server;

import com.twelvet.framework.core.annotation.EnableTWTFeignClients;
import com.twelvet.framework.core.annotation.EnableTwelveTConfig;
import com.twelvet.framework.security.annotation.EnableTWTResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.websocket.OnOpen;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 启动入口
 */
@EnableTWTResourceServer
@EnableTwelveTConfig
@EnableTWTFeignClients
@SpringBootApplication
public class NettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
	}

}
