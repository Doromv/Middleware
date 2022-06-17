package com.doromv.service;

import com.doromv.pojo.UserAddress;

import java.util.List;

/**
 * 用户服务
 * @author Doromv
 *
 */
public interface UserService {
	
	/**
	 * 按照用户id返回所有的收货地址
	 * @param userId
	 * @return
	 */
	public List<UserAddress> getUserAddressList(String userId);

}
