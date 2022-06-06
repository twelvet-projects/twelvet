package com.twelvet;

import com.twelvet.framework.core.annotation.EnableTwelveTConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 认证中心启动器
 * @EnableFeignClients 开启扫描服务
 */
@EnableTwelveTConfig
@EnableFeignClients
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
