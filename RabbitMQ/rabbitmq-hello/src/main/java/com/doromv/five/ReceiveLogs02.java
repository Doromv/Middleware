package com.doromv.five;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * @author Doromv
 * @Description 接收消息
 * @create 2022-06-12-9:08
 */
public class ReceiveLogs02 {

    //交换机名称
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //声明一个临时队列
        String queue = channel.queueDeclare().getQueue();
        //队列与交换机绑定
        channel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("02等待接收消息");
        channel.basicConsume(queue,true,
                (consumerTag,message)->{
            String msg= new String(message.getBody(),"UTF-8");
                    System.out.println("接收到的消息为"+msg);
                },
                (consumerTag)->{
                    System.out.println("接收消息失败");
                });
    }
}
