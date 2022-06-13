package com.doromv.springbootrabbitmq.consumer;

import com.doromv.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-13-15:43
 */
@Slf4j
@Component
public class DelayedQueueConsumer {

    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE)
    public void receiverDelayQueueMessage(Message message){

        String msg = new String(message.getBody());
        log.info("当前时间为{}，接收到延时消息的内容为{}",new Date().toString(),msg);
    }
}
