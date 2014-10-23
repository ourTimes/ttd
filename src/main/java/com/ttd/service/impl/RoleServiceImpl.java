/*
 * Class: RoleServiceImpl
 * Description:角色服务接口实现类
 * Version: 1.0
 */
package com.ttd.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttd.dao.BaseDao;
import com.ttd.dao.RoleDao;
import com.ttd.entity.Role;
import com.ttd.model.Page;
import com.ttd.service.RoleService;
import com.ttd.util.StringUtils;

/**
 * @author Gao
 * 
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements
		RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	protected BaseDao<Role, Long> getDao() {
		return roleDao;
	}

	@Override
	public Page<Role> findPage(Map parameterMap, int pageNo, int pageSize) {
		Page<Role> page = new Page<Role>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("from Role r where 1=1");
		if (!StringUtils.isBlank((String) parameterMap.get("roleName"))) {
			param.put("roleName", "%" + parameterMap.get("roleName") + "%");
			hql.append(" and r.name like :roleName");
		}
		roleDao.findPage(page, hql.toString(), param);
		return page;
	}

	@Override
	public Role getByRoleName(String roleName) {

		return roleDao.findUnique("from Role where name=?", roleName);
	}

}
