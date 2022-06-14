package com.doromv.springbootrabbitmq.consumer;

import com.doromv.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-14-15:02
 */
@Slf4j
@Component
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void receiveConfirm(Message message){
        String msg = new String(message.getBody());
        log.info("接收到的消息内容为：{}",msg);
    }
}
