package com.publicapi;


import com.publicapi.dynamicroute.service.dynamicrote.DynamicRouteService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableDubbo
public class GatewayApplicationStarter {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GatewayApplicationStarter.class);
        DynamicRouteService routeService = applicationContext.getBean(DynamicRouteService.class);
        routeService.init();
    }
}