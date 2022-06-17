package com.doromv.service.impl;


import com.doromv.service.OrderService;
import com.doromv.service.UserService;

/**
 * @author Doromv
 * @Description 1.将服务提供者注册到注册中心(暴露服务)
 *              导入dubbo依赖,配置服务提供者
 *              2.让服务消费者去注册中心订阅服务提供者的服务地址
 * @create 2022-06-17-14:57
 */
public class OrderServiceImpl implements OrderService {

    UserService userService;
    public void initOrder(String user) {
        //1.查询用户的收货地址
    }
}
