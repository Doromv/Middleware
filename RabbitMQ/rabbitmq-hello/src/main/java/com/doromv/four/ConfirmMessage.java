package com.doromv.four;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;

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
//        publishMessageBatch();
        //异步确认消息
        //发布1000个异步确认消息，耗时64毫秒
        publishMessageAsync();
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
            channel.basicPublish("",queueName,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
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
            channel.basicPublish("",queueName,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
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
        System.out.println("发布"+MESSAGE_COUNT+"个批量确认消息，耗时"+(end-begin)+"毫秒");
    }

    //异步发布确认
    public static void publishMessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列名称
        String queueName= String.valueOf(UUID.randomUUID());
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        /**
         * 线程安全有序的一个跳表 适用于高并发情况
         * 1.可以轻松的将序号与消息进行关联
         * 2.通过序号轻松批量删除条目
         * 3.支持多线程
         */
        ConcurrentSkipListMap<Long, String> map = new ConcurrentSkipListMap<>();
        //开始的时间
        long begin = System.currentTimeMillis();
        //准备消息的监听器，监听哪些消息发送成功了，哪些消息发送失败了
        channel.addConfirmListener(
                (deliveryTag,multiple)->{
            /**
             * 消息确认成功回调函数
             * 第一个参数：当前消息的标记（索引）
             * 第二个参数：是否为批量确认
             */
            //删除掉以及确认的消息
            if(multiple){
                map.headMap(deliveryTag).clear();
            }else {
                map.remove(deliveryTag);
            }
             //打印确认的消息
             System.out.println("确认的消息"+deliveryTag);
        },
                (deliveryTag,multiple)->{
            /**
             * 消息确认失败回调函数
             * 第一个参数：当前消息的标记（索引）
             * 第二个参数：是否为批量确认
             */
            //打印未确认的消息
            System.out.println("未确认的消息"+deliveryTag);
        });
        //批量发送消息
        for (int i=0;i<MESSAGE_COUNT;i++){
            String message=i+"";

            //此处记录所有要发送的消息
            map.put(channel.getNextPublishSeqNo(),message);

            channel.basicPublish("",queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
        }
        //结束的时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"个异步确认消息，耗时"+(end-begin)+"毫秒");
    }
}
