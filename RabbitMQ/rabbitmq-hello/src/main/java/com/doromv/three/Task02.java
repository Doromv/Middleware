package com.doromv.three;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import jdk.internal.dynalink.beans.StaticClass;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author Doromv
 * @Description 实现消息消费过程中不丢失消息、放回队列中重新消费
 * @create 2022-06-10-11:00
 */
public class Task02 {

    //队列名称
    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        //开启发布确认
        channel.confirmSelect();

        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息："+message);
        }
    }
}
