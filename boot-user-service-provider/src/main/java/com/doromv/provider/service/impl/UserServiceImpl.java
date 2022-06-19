package com.doromv.provider.service.impl;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Component
@Service
//@com.alibaba.dubbo.config.annotation.Service(timeout = 2000)
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
public class UserServiceImpl implements UserService {

	//服务默认1秒超时，模拟超时情况：Thread.sleep(4000)。
	@Override
	public List<UserAddress> getUserAddressList(String userId) {
		System.out.println("UserServiceImpl.....old...");
		// TODO Auto-generated method stub
		UserAddress address1 = new UserAddress(1, "北京市昌平区宏福科技园综合楼3层", "1", "李老师", "010-56253825", "Y");
		UserAddress address2 = new UserAddress(2, "深圳市宝安区西部硅谷大厦B座3层（深圳分校）", "1", "王老师", "010-56253825", "N");
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return Arrays.asList(address1,address2);
	}

}
