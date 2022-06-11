package com.doromv.four;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Doromv
 * @Description 发布确认模式
 * 1.单个确认模式
 * 2.批量确认模式
 * 3.异步批量确认模式
 * 比较使用时间确认哪种方式是最好的
 * @create 2022-06-11-15:58
 */
public class ConfirmMessage {

    //批量发消息的个数
    public static final int MESSAGE_COUNT=1000;

    public static void main(String[] args) throws Exception {
        //单个确认
        // 发布1000个消息，单独确认消息，耗时1476毫秒
//        publishMessageIndividually();
        //批量发送
        //发布1000个消息，批量确认消息，耗时405毫秒
        publishMessageBatch();
    }

    //单个发布确认
    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列名称
        String queueName= String.valueOf(UUID.randomUUID());
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始的时间
        long begin = System.currentTimeMillis();
        for (int i=0;i<MESSAGE_COUNT;i++){
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes(StandardCharsets.UTF_8));
            //单个消息就马上进行发布确认
            boolean flag=channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功");
            }
        }
        //结束的时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"个单独确认消息，耗时"+(end-begin)+"毫秒");
    }

    //批量发布确认
    public static void publishMessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列名称
        String queueName= String.valueOf(UUID.randomUUID());
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //批量确认消息的大小
        Integer batchSize=100;
        //开始的时间
        long begin = System.currentTimeMillis();
        for (int i=0;i<MESSAGE_COUNT;i++){
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes(StandardCharsets.UTF_8));
            //判断达到100条消息的时候，批量确认一次
            if(i%batchSize==0){
                boolean flag = channel.waitForConfirms();
                if(flag){
                    System.out.println("消息发布成功");
                }
            }
        }
        //结束的时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"个单独确认消息，耗时"+(end-begin)+"毫秒");
    }
}
