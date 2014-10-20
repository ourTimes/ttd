package com.ttd.dao.user;

import com.ttd.dao.BaseDao;
import com.ttd.entity.User;

public class UserDao extends BaseDao<User> {

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

}
