package com.publicapi.dynamicroute.service.listener.routeLisner;

import cn.hutool.core.util.ObjectUtil;
import com.publicapi.dynamicroute.service.strategy.RouteMessage.RouteMessageStrategy;
import com.publicapi.dynamicroute.service.strategy.RouteMessage.RouteMessageStrategyFactory;
import com.publicapi.modal.mq.MQMessage;
import com.publicapi.modal.mq.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



/**
 * queuesToDeclare 如果queue不存在则创建
 */
@RabbitListener(queuesToDeclare = @Queue(RabbitMqConstants.GATEWAY_QUEUE_NAME))
@Slf4j
@Component
public class RouteMessageListener {

    @RabbitHandler
    public void handler(MQMessage message){
        log.info("接收到消息 message: {}",message);
        RouteMessageStrategy routeMessageStrategy = RouteMessageStrategyFactory.getRouteMessageStrategy(message.getMessageType());
        if (ObjectUtil.isEmpty(routeMessageStrategy)){
            log.info("该消息无法处理");
            return ;
        }
        if (routeMessageStrategy.handle(message)) {
            log.info("消息处理成功");
        }else {
            log.info("消息处理失败");
            // todo 消息处理失败后的处理
        }
    }
}
