package com.doromv.two;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * @author Doromv
 * @Description 这是一个工作线程相当于消费者
 * @create 2022-06-09-11:53
 */
public class Worker01 {

    //队列名称
    public static final String QUEUE_NAME="work";
    //接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C3等待接受消息......");
               /* 接受消息
                * 1.消费哪个队列
                * 2.消费成功之后是否要自动答应,true代表自动答应，false代表手动答应
                * 3.消费者成功消费的回调函数
                * 4.消费者未成功消费的回调
                */
        channel.basicConsume(QUEUE_NAME,true, (consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        }, (tag)->{
            System.out.println(tag+"接受消息失败");
        });
    }
}
