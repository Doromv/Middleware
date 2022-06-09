package com.doromv.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Doromv
 * @Description 生产者：发消息
 * @create 2022-06-09-10:38
 */
public class Producer {

    //队列名称
    public static final String QUEUE_NAME="hello";
    //发消息
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
         * 创建一个队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化，默认情况下消息存储在内存中，持久化之后存放到磁盘中
         * 3.该队列是否只供一个消费者消费，是否进行消息共享，true可以多个消费者消费，false只能一个消费者消费
         * 4.是否自动删除，最后一个消费者断开连接后，该队列是否自动删除，true自动删除，false不自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 发送一个消息
         * 1.发送到哪个交换机
         * 2.路由的key值是哪个
         * 3.其他的参数信息
         * 4.发送消息的消息体
         */
        String message="Doromv";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送完毕！");

        //释放资源
        channel.close();
        connection.close();
    }
}
