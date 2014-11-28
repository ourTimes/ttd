package com.ttd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttd.dao.BaseDao;
import com.ttd.dao.RegisterDao;
import com.ttd.entity.User;
import com.ttd.service.RegisterService;

/**
 * @author gaocy
 * 
 */
@Service
public class RegisterServiceImpl extends BaseServiceImpl<User, Integer>
implements RegisterService{
	@Autowired
	private RegisterDao registerDao;
	
	@Override
	protected BaseDao<User, Integer> getDao() {
		return registerDao;
	}

//	public void creat(User user){
//		registerDao.create(user);
//	}
}
