/*
 * Class: AuthorityService
 * Description:权限服务接口
 * Version: 1.0
 */
package com.ttd.service;

import java.util.List;
import java.util.Map;

import com.ttd.entity.Authority;
import com.ttd.model.Page;

/**
 * @author gao
 * 
 */
public interface AuthorityService extends BaseService<Authority, Long> {

	/**
	 * 根据用户登录名查询所具备的可用权限代码列表
	 * 
	 * @param userLoginId
	 * @return
	 */
	List<String> getEnabledAuthorityCodesByUserLoginId(String userLoginId);

	/**
	 * 分页查询权限列表
	 * @param parameterMap 查询参数
	 * @param pageNo 页码
	 * @param pageSize 页大小
	 * @return
	 */
	Page<Authority> findPage(Map<String,?> parameterMap, int pageNo, int pageSize);

	/**
	 * 根据名称查询权限
	 * @param authorityName
	 * @return 名称对应的权限，不存在则返回null
	 */
	Authority getByAuthorityName(String authorityName);
}
