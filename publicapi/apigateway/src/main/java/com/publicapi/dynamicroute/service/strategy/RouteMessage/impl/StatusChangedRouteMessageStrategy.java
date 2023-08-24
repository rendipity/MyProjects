package com.publicapi.dynamicroute.service.strategy.RouteMessage.impl;

import com.publicapi.constants.APIConstants;
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
public class StatusChangedRouteMessageStrategy implements RouteMessageStrategy, InitializingBean {

    @Resource
    private DynamicRouteService dynamicRouteService;
    @Resource
    private ApiResourceConvert apiResourceConvert;
    @Override
    public String getHandlerType() {
        return RabbitMqConstants.STATUS_CHANGED_ROUTE_MESSAGE;
    }

    @Override
    public Boolean handle(MQMessage message) {
        ApiResourceDTO apiDTO = (ApiResourceDTO)message.getContent();
        if (apiDTO.getStatus().equals(APIConstants.ENABLE)){
            dynamicRouteService.addRoute(apiResourceConvert.dto2modal(apiDTO));
        } else if (apiDTO.getStatus().equals(APIConstants.DISABLE)) {
            dynamicRouteService.removeRoute(apiDTO.getCode());
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RouteMessageStrategyFactory.addRouteMessageStrategy(getHandlerType(), this);
    }
}
