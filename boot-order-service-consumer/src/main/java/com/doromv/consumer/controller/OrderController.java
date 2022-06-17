package com.doromv.consumer.controller;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-17-16:19
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/initOrder/{uid}")
    public List<UserAddress> initOrder(@PathVariable("uid") String uid){
        return  orderService.initOrder(uid);
    }
}
