/*
 * Class: UserDao
 * Description:用户对象持久层接口
 * Version: 1.0
 */
package com.ttd.dao;

import java.util.Map;

import com.ttd.entity.User;
import com.ttd.model.Page;

/**
 * 
 * @author gao
 * 
 */
public interface UserDao extends BaseDao<User, Long> {
	/**
	 * 根据用户登录名查询用户
	 * 
	 * @param userLoginId
	 *            用户登录名
	 */
	public User findByUserLoginId(String userLoginId);

	/**
	 * 分页查询
	 * @param parameterMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<User> findPage(Map parameterMap, int pageNo, int pageSize);
}
