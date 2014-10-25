/*
 * Class: AuthorityDao
 * Description:权限对象持久层接口
 * Version: 1.0
 */
package com.ttd.dao;

import java.util.List;
import java.util.Map;

import com.ttd.entity.Authority;
import com.ttd.model.Page;

/**
 * 
 * @author gaowenming
 * 
 */
public interface AuthorityDao extends BaseDao<Authority, Long> {

	/**
	 * 根据用户登录名查询所具备的可用权限代码列表
	 * 
	 * @param userLoginId
	 * @return
	 */
	List<String> getEnabledAuthorityCodesByUserLoginId(String userLoginId);

	/**
	 * 根据名称查询权限
	 * 
	 * @param authorityName
	 * @return
	 */
	Authority getByAuthorityName(String authorityName);

	/**
	 * 分页查询
	 * 
	 * @param parameterMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<Authority> findPage(Map<String,?> parameterMap, int pageNo, int pageSize);

}
