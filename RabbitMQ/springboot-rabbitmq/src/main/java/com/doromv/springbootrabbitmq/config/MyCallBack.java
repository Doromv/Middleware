package com.doromv.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-14-15:21
 */
@Configuration
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //当前的类注入到ConfirmCallback
    @PostConstruct
    public void init(){

        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
    /**
     * 交换机确认回调方法
     * 参数1：保存回调消息的id以及相关信息
     * 参数2：交换机成功收到消息则为true，反之为false
     * 参数3：如果参数二为false，参数三则为接收消息失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id= correlationData.getId()!=null?correlationData.getId():"";

        if(b){
            log.info("交换机成功收到id为{}的消息",id);
        }else {
            log.info("交换机还未收到id为{}的消息，原因是{}",id,s);
        }
    }

    //当消息传递过程中不可达目的地时将消息返回给生产者。
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息{}，被交换机{}退回，退回原因：{}，路由Key为{}",
                new String(returnedMessage.getMessage().getBody()),returnedMessage.getExchange(),
                returnedMessage.getReplyText(),returnedMessage.getRoutingKey());
    }
}
