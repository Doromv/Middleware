package com.doromv.eight;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * @author Doromv
 * @Description 死信队列实战
 * 生产者
 * @create 2022-06-12-21:11
 */
public class Producer {

    //普通交换机名称
    public static final String NORMAL_EXCHANGE="normal_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        //死信消息 设置TTL时间 单位毫秒
//        AMQP.BasicProperties properties
//                = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 1; i <=10; i++) {
            String msg=i+"";
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null, msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("发布了消息："+msg);
        }
    }
}
