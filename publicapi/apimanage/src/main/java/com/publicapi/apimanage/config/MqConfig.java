package com.publicapi.apimanage.config;

import com.publicapi.modal.mq.RabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    /**
     * 创建Bean 队列
     */
    @Bean
    public Queue gatewayQueue(){
        return new Queue(RabbitMqConstants.GATEWAY_QUEUE_NAME,true,false,false);
    }


    /**
     * 创建一个交换机bean
     */
    @Bean
    public FanoutExchange gatewayExchange(){
        return new FanoutExchange(RabbitMqConstants.GATEWAY_EXCHANGE_NAME);
    }

    /**
     * 绑定direct交换机和队列1
     */
    @Bean
    public Binding bindQueue1DirectChange(){
        return  BindingBuilder
                .bind(gatewayQueue())
                .to(gatewayExchange());
    }
}
