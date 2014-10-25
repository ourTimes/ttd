/*
 * Class: UserServiceImpl
 * Description:用户服务接口实现类
 * Version: 1.0
 */
package com.ttd.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttd.dao.BaseDao;
import com.ttd.dao.UserDao;
import com.ttd.entity.User;
import com.ttd.model.Page;
import com.ttd.service.UserService;

/**
 * @author Gao
 * 
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements
		UserService {

	@Autowired
	private UserDao userDao;

	@Override
	protected BaseDao<User, Long> getDao() {
		return userDao;
	}

	@Override
	public User getByUserLoginId(String userLoginId) {
		return userDao.findByUserLoginId(userLoginId);
	}

	@Override
	public Page<User> findPage(Map<String,?> parameterMap, int pageNo, int pageSize) {
		return userDao.findPage(parameterMap,pageNo,pageSize);
	}

	
	}

