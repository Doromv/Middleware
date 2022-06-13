package com.doromv.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-13-9:00
 */
@Configuration
public class TtlQueueConfig {

    //普通交换机名称
    public static final String NORMAL_EXCHANGE = "X";
    //死信交换机名称
    public static final String DEAD_LETTER_EXCHANGE = "Y";
    //普通队列名称
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    //死信队列名称
    public static final String DEAD_LETTER_QUEUE = "QD";

    //声明普通交换机
    @Bean("xExchange")
    public DirectExchange normalExchange(){
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    //声明死信交换机
    @Bean("yExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    //声明普通队列
    @Bean("aQueue")
    public Queue aQueue(){
        Map<String, Object> arguments=new HashMap<>();
        //设置队列对应的死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE);
        //设置RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置消息过期时间(TTL)
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }
    @Bean("bQueue")
    public Queue bQueue(){
        Map<String, Object> arguments=new HashMap<>();
        //设置队列对应的死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE);
        //设置RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置消息过期时间(TTL)
        arguments.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }
    @Bean("cQueue")
    public Queue cQueue(){
        Map<String, Object> arguments=new HashMap<>();
        arguments.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
    }
    //声明死信队列
    @Bean("dQueue")
    public Queue dQueue(){

        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }
    //进行板顶
    @Bean
    public Binding aQueueBindingX
    (@Qualifier("aQueue") Queue aQueue,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(aQueue).to(xExchange).with("XA");
    }

    @Bean
    public Binding bQueueBindingX
            (@Qualifier("bQueue") Queue bQueue,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(bQueue).to(xExchange).with("XB");
    }

    @Bean
    public Binding dQueueBindingY
            (@Qualifier("dQueue") Queue dQueue,@Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(dQueue).to(yExchange).with("YD");
    }

    @Bean
    public Binding cQueueBindingY
            (@Qualifier("cQueue") Queue cQueue,@Qualifier("xExchange") DirectExchange xExchange){

        return BindingBuilder.bind(cQueue).to(xExchange).with("XC");
    }
}
