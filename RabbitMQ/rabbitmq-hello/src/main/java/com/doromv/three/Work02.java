package com.doromv.three;

import com.doromv.utils.RabbitMqUtils;
import com.doromv.utils.SleepUtils;
import com.rabbitmq.client.Channel;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-10-11:14
 */
public class Work02 {

    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("C1等待接收消息，处理时间较短");

        channel.basicConsume(TASK_QUEUE_NAME, false, (consumeTag,message)->{
            String str = new String(message.getBody(),"UTF-8");
            SleepUtils.sleep(1);
            System.out.println("接收到消息:"+str);
            //手动应答
            /**
             * 1.消息标记 tag
             * 2.是否批量应答未应答消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        }, (consumerTag)->{
            System.out.println("消费者取消消费");
        });
    }
}
