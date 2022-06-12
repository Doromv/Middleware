package com.doromv.six;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-12-9:51
 */
public class ReceiveLogsDirect01 {

    //交换机名称
    public static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.queueDeclare("console",false,false,false,null);

        channel.queueBind("console", EXCHANGE_NAME, "info");

        channel.queueBind("console", EXCHANGE_NAME, "warning");

        channel.basicConsume("console", false,
                (consumerTag,message)->{
                    String msg= new String(message.getBody(),"UTF-8");
                    System.out.println("接收到的消息为"+msg);
                },
                (consumerTag)->{
                    System.out.println("接收消息失败");
                });
    }
}
