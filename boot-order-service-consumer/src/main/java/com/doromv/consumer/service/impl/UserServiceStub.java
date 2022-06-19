package com.doromv.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-19-15:31
 */

public class UserServiceStub implements UserService {

    private UserService userService;

    public UserServiceStub(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        System.out.println("UserServiceStub");
        if(StringUtils.hasText(userId)){
            return userService.getUserAddressList(userId);
        }
        return null;
    }
}
