package com.publicapi.apimanage.core.service.mq.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.publicapi.apimanage.core.service.mq.MqService;
import com.publicapi.modal.mq.MQMessage;
import com.publicapi.modal.mq.RabbitMqConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MqServiceImpl implements MqService {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public Boolean sendMqMessage(String type, Object content) {
        MQMessage message = new MQMessage();
        message.setId(IdUtil.nanoId(10));
        message.setSendTime(DateUtil.now());
        message.setMessageType(type);
        message.setContent(content);
        rabbitTemplate.convertAndSend(RabbitMqConstants.GATEWAY_EXCHANGE_NAME,RabbitMqConstants.GATEWAY_QUEUE_NAME, message);
        return true;
    }


}
