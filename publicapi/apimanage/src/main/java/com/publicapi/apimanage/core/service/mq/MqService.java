package com.publicapi.apimanage.core.service.mq;

public interface MqService {


    public Boolean sendMqMessage(String type, Object content);
}
