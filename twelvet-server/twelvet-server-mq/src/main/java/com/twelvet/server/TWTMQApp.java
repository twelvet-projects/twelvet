package com.twelvet.server;

import com.twelvet.framework.core.annotation.EnableTWTFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 启动程序
 */
@EnableTWTFeignClients
@SpringBootApplication
public class TWTMQApp {

    public static void main(String[] args) {
        SpringApplication.run(TWTMQApp.class, args);
    }

}
