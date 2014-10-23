/*
 * Class: UserService
 * Description:用户服务接口
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-6-18
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.service;

import java.util.Map;

import com.ttd.entity.User;
import com.ttd.model.Page;

/**
 * @author gao
 *
 */
public interface UserService extends BaseService<User,Long> {

	/**
	 * 根据用户登录id查询用户
	 * @param userLoginId 用户登录id
	 * @return 用户实例对象
	 */
	User getByUserLoginId(String userLoginId);

	/**
	 * 分页查询用户列表
	 * 
	 * @param parameterMap
	 *            查询参数
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return
	 */
	Page<User> findPage(Map parameterMap, int pageNo, int pageSize);
	/**
	 * 将ShippingPlan转化为ShippingPlanDto
	 * @param entity
	 * @return
	 */

}
