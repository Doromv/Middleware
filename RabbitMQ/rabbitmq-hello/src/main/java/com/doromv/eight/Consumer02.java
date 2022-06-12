package com.doromv.eight;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;


/**
 * @author Doromv
 * @Description
 * @create 2022-06-12-21:39
 */
public class Consumer02 {

    private static final String DEAD_EXCHANGE = "dead_exchange";

    public static final String DEAD_QUEUE="dead_queue";

    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("等待接收死信队列消息");
        channel.basicConsume(DEAD_QUEUE,true,
                (consumerTag,message)->{
                    System.out.println("02收到的消息为"+new String(message.getBody(),"UTF-8"));
                },
                (consumerTag)->{
                    System.out.println("未接收到消息");
                });
    }
}
