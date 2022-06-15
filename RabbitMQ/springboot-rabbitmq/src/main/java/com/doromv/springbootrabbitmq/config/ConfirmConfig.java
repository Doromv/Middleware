package com.doromv.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    //备份交换机名称
    public static final String BACKUP_EXCHANGE="backup_exchange";
    //备份队列名称
    public static final String BACKUP_QUEUE="backup_queue";
    //警告队列名称
    public static final String WARNING_QUEUE="warning_queue";
    @Bean
    public DirectExchange confirmExchange(){

        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).withArgument("alternate-exchange",BACKUP_EXCHANGE).build();
    }

    @Bean
    public FanoutExchange backupExchange(){

        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Queue confirmQueue(){

        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Queue backupQueue(){

        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    @Bean
    public Queue warningQueue(){

        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean
    public Binding confirmExchangeBindingConfirmQueue
            (@Qualifier("confirmExchange") DirectExchange confirmExchange,
             @Qualifier("confirmQueue") Queue confirmQueue){

        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public Binding backupExchangeBindingBackupQueue
            (@Qualifier("backupExchange") FanoutExchange backupExchange,
             @Qualifier("backupQueue") Queue backupQueue){

        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Binding backupExchangeBindingWarningQueue
            (@Qualifier("backupExchange") FanoutExchange backupExchange,
             @Qualifier("warningQueue") Queue warningQueue){

        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
