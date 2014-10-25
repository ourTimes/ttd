/*
 * Class: AuthorityDaoImpl
 * Description:权限对象持久层实现类
 * Version: 1.0
 */
package com.ttd.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ttd.dao.AuthorityDao;
import com.ttd.entity.Authority;
import com.ttd.model.Page;
import com.ttd.util.StringUtils;

/**
 * 
 * @author gao
 * 
 */
@Component
public class AuthorityDaoImpl extends BaseDaoImpl<Authority, Long> implements
		AuthorityDao {

	@Override
	public List<String> getEnabledAuthorityCodesByUserLoginId(String userLoginId) {
		String hql = "select au.code from User u "
				+ " join u.roles r with r.enabled  = true "
				+ " join r.auth au with au.enabled = true "
				+ " where u.loginId=:userLoginId";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userLoginId", userLoginId);
		List<?> list = this.findObjlist(hql, param);
		List<String> result = new ArrayList<String>();
		for (Object obj : list) {
			result.add((String) obj);
		}
		return result;
	}

	@Override
	public Authority getByAuthorityName(String authorityName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("authorityName", authorityName);
		return this.findUnique("from Authority where name =:authorityName",
				param);
	}

	@Override
	public Page<Authority> findPage(Map<String,?> parameterMap, int pageNo, int pageSize) {
		Page<Authority> page = new Page<Authority>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		StringBuffer hql = new StringBuffer("from Authority a where 1=1 ");
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtils.isBlank((String) parameterMap.get("authorityName"))) {
			param.put("authorityName", "%" + parameterMap.get("authorityName")
					+ "%");
			hql.append(" and a.name like :authorityName");
		}
		this.findPage(page, hql.toString(), param);
		return page;
	}

}
