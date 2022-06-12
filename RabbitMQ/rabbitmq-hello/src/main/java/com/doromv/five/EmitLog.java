package com.doromv.five;

import com.doromv.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author Doromv
 * @Description 发送消息
 * @create 2022-06-12-9:25
 */
public class EmitLog {

    //交换机名称
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"S",null, msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("成功发出消息"+msg);
        }
    }
}
