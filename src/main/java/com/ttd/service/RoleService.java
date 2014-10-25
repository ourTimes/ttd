/*
 * Class: RoleService
 * Description:角色服务接口
 * Version: 1.0
 */
package com.ttd.service;

import java.util.Map;

import com.ttd.entity.Role;
import com.ttd.model.Page;

/**
 * @author gao
 * 
 */
public interface RoleService extends BaseService<Role, Long> {

	/**
	 * 分页查询角色列表
	 * 
	 * @param parameterMap
	 *            查询参数
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return
	 */
	Page<Role> findPage(Map<String,?> parameterMap, int pageNo, int pageSize);

	/**
	 * 根据名称查询角色
	 * @param authorityName
	 * @return 名称对应的权限，不存在则返回null
	 */
	Role getByRoleName(String roleName);
	
}
