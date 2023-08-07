package com.publicapi.apimanage;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.publicapi.apimanage.dao.mapper")
@EnableDubbo
public class ApiManageApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(ApiManageApplicationStarter.class);
    }
}
