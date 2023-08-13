package com.publicapi.dynamicroute.service.strategy.RouteMessage;

import com.publicapi.modal.mq.MQMessage;

public interface RouteMessageStrategy {

    Boolean handle(MQMessage message);

    String getHandlerType();
}
