/*
 * Class: UserDaoImpl
 * Description:用户对象持久层实现类
 * Version: 1.0
 */
package com.ttd.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ttd.dao.UserDao;
import com.ttd.entity.User;
import com.ttd.model.Page;
import com.ttd.util.StringUtils;

/**
 * 
 * @author gao
 * 
 */
@Component
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {


	public User findByUserLoginId(String userLoginId) {
		return findUnique("from User where loginId=?", userLoginId);
	}


	@Override
	public Page<User> findPage(Map parameterMap, int pageNo, int pageSize) {
		Page<User> page = new Page<User>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		StringBuffer hql = new StringBuffer("from User u where 1=1 ");
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtils.isBlank((String) parameterMap.get("userType"))) {
			//用户类型查询
			param.put("userType", parameterMap.get("userType"));
			hql.append(" and u.userType.metadataCode = :userType ");
		}
		if (!StringUtils.isBlank((String) parameterMap.get("userName"))) {
			//用户名称模糊查询
			param.put("userName", "%" + parameterMap.get("userName") + "%");
			hql.append(" and u.userName like :userName ");
		}
		this.findPage(page, hql.toString(), param);
		return page;
	}
}