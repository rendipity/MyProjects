package com.publicapi.dynamicroute.service.dynamicrote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class DynamicRouteApplicationEventPublisherAware implements ApplicationEventPublisherAware {

    //@Resource
    //DynamicRouteService dynamicRouteService;

    ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.info("ApplicationEventPublisherAware 执行了");
        this.applicationEventPublisher = applicationEventPublisher;
        //dynamicRouteService.init();
    }
}
