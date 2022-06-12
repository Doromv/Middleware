package com.doromv.eight;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Doromv
 * @Description 死信队列实战
 * 消费者01
 * @create 2022-06-12-20:36
 */
public class Consumer01 {

    //普通交换机名称
    public static final String NORMAL_EXCHANGE="normal_exchange";

    //死信交换机名称
    public static final String DEAD_EXCHANGE="dead_exchange";

    //普通队列名称
    public static final String NORMAL_QUEUE="normal_queue";

    //死信队列名称
    public static final String DEAD_QUEUE="dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        //声明普通交换机和死信交换机的类型为Direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        /**
         * 设置普通队列的arguments
         * 1.普通队列绑定对应的死信交换机绑定
         * 2.更改死信消息的RoutingKey,修改成 死信交换机与死信队列绑定的key值，保证死信消息可以direct到死信队列
         * 3.设置普通队列消息的过期时间
         * 4.设置普通队列的最大长度
         */
        Map<String, Object> arguments=new HashMap<>();
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);//1
        arguments.put("x-dead-letter-routing-key","lisi");//2
//        arguments.put("x-message-ttl",10000);//3
//        arguments.put("x-max-length",6);//4
        //声明普通队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        //普通交换机和普通队列绑定
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE, "zhangsan");
        //死信交换机和死信队列绑定
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE, "lisi");
        System.out.println("等待接收消息");
        channel.basicConsume(NORMAL_QUEUE,false ,
                (consumerTag,message)->{
                    String msg = new String(message.getBody(),"UTF-8");
                    if(msg.equals("5")){
                        System.out.println(msg+"被拒收");
                        channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
                    }else {
                        System.out.println("01收到的消息为"+msg);
                        channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                    }
                },
                (consumerTag)->{
                    System.out.println("未接收到消息");
                });
    }
}
