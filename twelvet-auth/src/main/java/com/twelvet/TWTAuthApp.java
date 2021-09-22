package com.twelvet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 认证中心启动器
 * @EnableFeignClients 开启扫描服务
 */
@EnableFeignClients
@SpringBootApplication
public class TWTAuthApp {

    public static void main(String[] args) {
        SpringApplication.run(TWTAuthApp.class, args);
    }

}
