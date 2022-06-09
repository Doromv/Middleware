package com.doromv.two;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.sun.org.apache.xpath.internal.functions.FuncNumber;

import java.util.Scanner;

/**
 * @author Doromv
 * @Description 生产者 发送大量消息
 * @create 2022-06-09-13:10
 */
public class Task01 {

    //队列名称
    public static final String QUEUE_NAME="work";

    //发送大量消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 创建一个队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化，默认情况下消息存储在内存中，持久化之后存放到磁盘中
         * 3.该队列是否只供一个消费者消费，是否进行消息共享，true可以多个消费者消费，false只能一个消费者消费
         * 4.是否自动删除，最后一个消费者断开连接后，该队列是否自动删除，true自动删除，false不自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            /**
             * 发送一个消息
             * 1.发送到哪个交换机
             * 2.路由的key值是哪个
             * 3.其他的参数信息
             * 4.发送消息的消息体
             */
            channel.basicPublish("",QUEUE_NAME, null,message.getBytes());
        }
    }
}
