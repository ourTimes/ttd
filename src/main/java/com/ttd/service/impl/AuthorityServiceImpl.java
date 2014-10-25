/*
 * Class: AuthorityServiceImpl
 * Description:权限服务接口实现类
 * Version: 1.0

 */
package com.ttd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttd.dao.AuthorityDao;
import com.ttd.dao.BaseDao;
import com.ttd.entity.Authority;
import com.ttd.model.Page;
import com.ttd.service.AuthorityService;

/**
 * @author gao
 * 
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority, Long>
		implements AuthorityService {

	@Autowired
	private AuthorityDao authorityDao;

	@Override
	protected BaseDao<Authority, Long> getDao() {
		return authorityDao;
	}

	@Override
	public List<String> getEnabledAuthorityCodesByUserLoginId(String userLoginId) {
		return authorityDao.getEnabledAuthorityCodesByUserLoginId(userLoginId);
	}

	@Override
	public Page<Authority> findPage(Map<String,?> parameterMap, int pageNo, int pageSize) {
		return authorityDao.findPage(parameterMap,pageNo,pageSize);
	}


	@Override
	public Authority getByAuthorityName(String authorityName) {
		return authorityDao.getByAuthorityName(authorityName);
	}


	@Override
	public void add(Authority entity) {
		if (authorityDao.getByAuthorityName(entity.getName()) != null) {
			
		}
		
		authorityDao.create(entity);
	}
}
