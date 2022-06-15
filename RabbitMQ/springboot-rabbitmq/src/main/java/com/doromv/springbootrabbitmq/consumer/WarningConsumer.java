package com.doromv.springbootrabbitmq.consumer;

import com.doromv.springbootrabbitmq.config.ConfirmConfig;
import com.doromv.springbootrabbitmq.config.MyCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-15-10:35
 */
@Slf4j
@RestController
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("报警发现不可路由消息：{}", msg);
    }
}
