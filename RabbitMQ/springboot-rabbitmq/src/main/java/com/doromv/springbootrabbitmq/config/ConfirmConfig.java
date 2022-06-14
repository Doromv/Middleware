package com.doromv.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Doromv
 * @Description 发布确认 高级内容
 * @create 2022-06-14-14:49
 */
@Configuration
public class ConfirmConfig {

    //交换机名称
    public static final String CONFIRM_EXCHANGE="confirm_exchange";
    //队列名称
    public static final String CONFIRM_QUEUE="confirm_queue";
    //routingKey
    public static final String CONFIRM_ROUTING_KEY="key1";

    @Bean
    public DirectExchange confirmExchange(){

        return new DirectExchange(CONFIRM_EXCHANGE);
    }

    @Bean
    public Queue confirmQueue(){

        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Binding confirmExchangeBindingConfirmQueue
            (@Qualifier("confirmExchange") DirectExchange confirmExchange,
             @Qualifier("confirmQueue") Queue confirmQueue){

        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }
}
