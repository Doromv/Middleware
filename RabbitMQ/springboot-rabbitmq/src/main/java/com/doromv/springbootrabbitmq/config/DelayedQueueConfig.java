package com.doromv.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sound.midi.Track;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-13-14:55
 */
@Configuration
public class DelayedQueueConfig {

    //交换机
    public static final String DELAYED_EXCHANGE="delayed.exchange";
    //队列
    public static final String DELAYED_QUEUE="delayed.queue";
    //routingKey
    public static final String DELAYED_ROUTING_KEY="delayed.routingKey";

    //创建交换器
    @Bean
    public CustomExchange delayedExchange(){

        /**
         * 1.交换机的名称
         * 2.交换机的类型
         * 3.交换器是否要持久化
         * 4.是否需要自动删除
         * 5.其他参数
         */
        Map<String, Object> arguments=new HashMap<>();
        arguments.put("x-delayed-type","direct");
        return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message", true,false,arguments);
    }

    @Bean
    public Queue delayedQueue(){

        return new Queue(DELAYED_QUEUE);
    }

    @Bean
    public Binding delayedQueueBindingDelayedExchange
            (@Qualifier("delayedQueue") Queue delayedQueue,
             @Qualifier("delayedExchange") CustomExchange delayedExchange){

        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
