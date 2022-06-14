package com.doromv.springbootrabbitmq.controller;

import com.doromv.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-14-14:58
 */
@Slf4j
@RestController()
@RequestMapping("/confirm")
public class ProducerController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //发送消息 测试确认
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message){

        CorrelationData correlationData1 = new CorrelationData(UUID.randomUUID().toString());
        CorrelationData correlationData2 = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,
                ConfirmConfig.CONFIRM_ROUTING_KEY,message,correlationData1);
        log.info("发送消息，内容为：{}",message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,
                ConfirmConfig.CONFIRM_ROUTING_KEY+"123",message,correlationData2);
        log.info("发送消息，内容为：{}",message);
    }
}
