package com.doromv.springbootrabbitmq.controller;

import com.doromv.springbootrabbitmq.config.DelayedQueueConfig;
import com.doromv.springbootrabbitmq.config.TtlQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Doromv
 * @Description 发送延迟消息
 * @create 2022-06-13-9:51
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessage {

    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMessage(@PathVariable String message){

        log.info("当前时间{}，发送{}给两个TTL队列",new Date().toString(),message);

        rabbitTemplate.convertAndSend(TtlQueueConfig.NORMAL_EXCHANGE,"XA","消息来自TTL为10的消息队列:"+message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.NORMAL_EXCHANGE,"XB","消息来自TTL为40的消息队列:"+message);
    }

    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendExpirationMsg(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间{}，发送时长为{},内容为{}的消息发送给一个TTL队列",new Date().toString(),ttlTime,message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.NORMAL_EXCHANGE,"XC",message,
                msg->{
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    //开始发消息 基于插件 发送消息及延迟的时间
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMessage(@PathVariable String message,@PathVariable Integer delayTime){

        log.info("当前时间{}，发送延长时间为{},内容为{}的消息给延时队列delayed.queue",new Date().toString(),delayTime,message);

        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE,DelayedQueueConfig.DELAYED_ROUTING_KEY,
                message,msg->{

            //设置延时的时长
                    msg.getMessageProperties().setDelay(delayTime);
                    return msg;
                });
    }
}
