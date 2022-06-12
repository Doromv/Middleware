package com.doromv.seven;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author Doromv
 * @Description 消费者
 * @create 2022-06-12-10:26
 */
public class ReceiveLogsTopic01 {

    //交换机名称
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queue="Q1";
        channel.queueDeclare(queue,false,false,false,null);
        channel.queueBind(queue,EXCHANGE_NAME,"*.orange.*");
        System.out.println("等待接收消息");
        channel.basicConsume(queue,false,
                (consumerTag,message)->{
                    String msg= new String(message.getBody(),"UTF-8");
                    System.out.println("接收队列为"+queue+"绑定键为"+message.getEnvelope().getRoutingKey()+"接收到的消息为"+msg);
                    channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                },
                (consumerTag)->{
                    System.out.println("接收消息失败");
                });
    }
}
