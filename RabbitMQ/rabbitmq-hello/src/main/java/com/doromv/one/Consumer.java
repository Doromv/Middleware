package com.doromv.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.concurrent.TimeoutException;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-09-10:38
 */
public class Consumer {

    //队列名称
    public static final String QUEUE_NAME="hello";
    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置IP 连接RabbitMQ的队列
        factory.setHost("192.168.200.130");
        //用户名
        factory.setUsername("Doromv");
        //密码
        factory.setPassword("QAQm..02r");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 接受消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动答应,true代表自动答应，false代表手动答应
         * 3.消费者成功消费的回调函数
         * 4.消费者未成功消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true,(consumerTag,message)->{
            String msg = new String(message.getBody());
            System.out.println(msg);
        },(consumerTag)->{
            System.out.println("消息消费被中断");
        });
    }
}
