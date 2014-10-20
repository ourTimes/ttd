/*
 * Class: BaseService
 * Description:基础服务接口
 * Version: 1.0
 */
package com.ttd.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author gao
 * 
 */
public interface BaseService<T, PK extends Serializable> {
	/**
	 * <b>使用listAll(boolean includeSoftDeletedEntity)替代</b><br>
	 * 查询所有对象(不包括deleteFlag为true的对象)
	 * 
	 * @return 所有对象的列表
	 */
	@Deprecated
	public List<T> listAll();

	/**
	 * 查询所有对象
	 * @param includeSoftDeletedEntity 包含软删除的entity，若为true，则将包含已软删除的所有信息
	 * @return
	 */
	public List<T> listAll(boolean includeSoftDeletedEntity);
	
	/**
	 * 添加对象
	 * 
	 * @param entity
	 */
	public void add(T entity);
	/**
	 * 添加所有对象
	 * 
	 * @param entities
	 */
	public void add(Collection<T> entities);
	
	/**
	 * add by chenchenxing at 2013 07 31
	 * 添加或修改所有的对象
	 * 
	 * @param entities
	 */
	public void addOrUpdate(Collection<T> entities);
	
	/**
	 * add by chenchenxing at 2013 08 05
	 * 添加或修改单一的对象
	 * @param entity
	 */
	public void addOrUpdate(T entity);

	/**
	 * 根据主键删除对象
	 * 
	 * @param key
	 */
	public void deleteById(PK key);
	
	/**
	 * 批量根据主键删除对象
	 * @param keys
	 */
	public void deleteById(List<PK> keys);

	/**
	 * 根据主键查询对象
	 * 
	 * @param key
	 * @return 查询结果
	 */
	public T get(PK key);

	/**
	 * 更新对象
	 * 
	 * @param entity
	 */
	public void update(T entity);
	/**
	 * 更新所有对象
	 * 
	 * @param entities
	 */
	public void update(Collection<T> entities);
	
	/**
	 * 通过键值对的形式返回全部的查询结果
	 * @param includeSoftDeletedEntity 包含软删除的entity，若为true，则将包含已软删除的所有信息
	 * @return
	 */
	public Map<PK, T> getAllByMap(boolean includeSoftDeletedEntity);

	/**
	 * 根据id集合查询对象
	 * @param ids
	 * @return
	 */
	Collection<T> findByIds(Collection<PK> ids);
	
	/**
	 * 根据id集合查询对象,通过键值对的形式返回全部的查询结果
	 * @param ids
	 * @return
	 */
	Map<PK, T> findMapByIds(Collection<PK> ids);

	/**
	 * 脱离session
	 * @param entity
	 */
	void evit(T entity);
}
