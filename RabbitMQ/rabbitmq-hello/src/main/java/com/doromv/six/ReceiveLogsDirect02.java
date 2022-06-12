package com.doromv.six;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-12-9:51
 */
public class ReceiveLogsDirect02 {

    //交换机名称
    public static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.queueDeclare("disk",false,false,false,null);

        channel.queueBind("disk", EXCHANGE_NAME, "error");
         
        channel.basicConsume("disk", false,
                (consumerTag,message)->{
                    String msg= new String(message.getBody(),"UTF-8");
                    System.out.println("接收到的消息为"+msg);
                },
                (consumerTag)->{
                    System.out.println("接收消息失败");
                });
    }
}
