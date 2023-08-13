package com.publicapi.dynamicroute.service.strategy.RouteMessage.impl;

import com.publicapi.dynamicroute.service.dynamicrote.DynamicRouteService;
import com.publicapi.dynamicroute.service.dynamicrote.convert.ApiResourceConvert;
import com.publicapi.dynamicroute.service.strategy.RouteMessage.RouteMessageStrategy;
import com.publicapi.dynamicroute.service.strategy.RouteMessage.RouteMessageStrategyFactory;
import com.publicapi.modal.api.ApiResourceDTO;
import com.publicapi.modal.mq.MQMessage;
import com.publicapi.modal.mq.RabbitMqConstants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RemoveRouteMessageStrategy implements RouteMessageStrategy, InitializingBean {

    @Resource
    private DynamicRouteService dynamicRouteService;

    @Override
    public String getHandlerType() {
        return RabbitMqConstants.REMOVE_ROUTE_MESSAGE;
    }

    @Override
    public Boolean handle(MQMessage message) {
        String apiCode = (String)message.getContent();
        dynamicRouteService.removeRoute(apiCode);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RouteMessageStrategyFactory.addRouteMessageStrategy(getHandlerType(), this);
    }
}
