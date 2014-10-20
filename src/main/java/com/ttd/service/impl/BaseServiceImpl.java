/*
 * Class: BaseServiceImpl
 * Description:基础服务接口实现类
 * Version: 1.0
 */
package com.ttd.service.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ttd.dao.BaseDao;
import com.ttd.service.BaseService;


/**
 * @author ga0
 * 
 */
public abstract class BaseServiceImpl<T, PK extends Serializable> implements
		BaseService<T, PK> {
	/**
	 * 需由子类实现的获取dao方法
	 * 
	 * @return 具体的dao实例
	 */
	protected abstract BaseDao<T, PK> getDao();

	@Override
	public List<T> listAll() {
		//不包含已删除对象
		return listAll(false);
	}
	@Override
	public List<T> listAll(boolean includeSoftDeletedEntity) {
		
		return getDao().getAll(includeSoftDeletedEntity);
	}
	@Override
	public void evit(T entity){
		getDao().evit(entity);
	}
	
	@Override
	public void add(T entity) {
		getDao().create(entity);
	}

	@Override
	public void add(Collection<T> entities) {
		if (entities != null && entities.size() > 0) {
			for (T entity : entities) {
				add(entity);
			}
		}
	}

	@Override
	public void update(Collection<T> entities) {
		if (entities != null && entities.size() > 0) {
			for (T entity : entities) {
				update(entity);
			}
		}
	}
	
	/**
	 * add by chenchenxing at 2013 07 31
	 */
	public void addOrUpdate(Collection<T> entities) {
		if(entities != null && entities.size() > 0) {
			for(T entity: entities) {
				addOrUpdate(entity);
			}
		}
	}
	
	/**
	 * add by chenchenxing at 2013 08 05
	 * @param entity
	 */
	public void addOrUpdate(T entity) {
		getDao().createOrUpdate(entity);
	}

	@Override
	public void deleteById(PK key) {
		getDao().deleteById(key);

	}
	
	/**
	 * add by chenchenxing at 2013 08 16
	 * @param keys
	 */
	@Override
	public void deleteById(List<PK> keys) {
		for(PK key: keys) {
			deleteById(key);
		}
	}

	@Override
	public T get(PK key) {
		return getDao().get(key);
	}
	
	@Override
	public Collection<T> findByIds(Collection<PK> ids) {
		return getDao().findByIds(ids);
	}

	@Override
	public void update(T entity) {
		getDao().update(entity);
	}

	/**
	 * 当前的paramMap中带有查询条件，并且查询条件都是模糊查询时，可以使用此方法自动根据Map中的key生成对应的hql
	 * Modify by GaoHuanquan 将paramMap中的String改为泛型?
	 * @param hql
	 * @param paramMap
	 */
	public void createHql(StringBuilder hql, Map<String,?> paramMap) {
		if (paramMap != null) {
			Iterator<String> it = paramMap.keySet().iterator();
			if (it.hasNext()) {
				hql.append(" where 1=1");
			}
			while (it.hasNext()) {
				String key = (String) it.next();
				hql.append(" and ").append(key).append(" like :").append(key);
			}
		}
	}
	/**
	 * add by GaohuanQuan
	 * 当前的paramMap中带有查询条件，并且查询条件都是模糊查询时，可以使用此方法自动根据Map中的key生成对应的hql
	 * @param hql
	 * @param paramMap
	 */
	public void createHqlCommon(StringBuilder hql, Map<String,?> paramMap) {
		if (paramMap != null) {
			Iterator<String> it = paramMap.keySet().iterator();
			if (it.hasNext()) {
				hql.append(" where 1=1");
			}
			while (it.hasNext()) {
				String key =  it.next();
				Object object=paramMap.get(key);
				Class<?> class1=object.getClass();
				String name=class1.getSimpleName();
				String value=key;
				if (key.contains(".")) {
					int index=key.lastIndexOf(".");
					value=key.substring(index+1);
				}
				if (name.equals("String")) {
					hql.append(" and ").append(key).append(" like :").append(value);
				}
				else {
					hql.append(" and ").append(key).append(" = :").append(value);
				}
			}
		}
	}
	
	@Override
	public Map<PK, T> getAllByMap(boolean includeSoftDeletedEntity) {
		return getDao().getAllByMap(includeSoftDeletedEntity);
	}
	
	@Override
	public Map<PK, T> findMapByIds(Collection<PK> ids){
		return getDao().findMapByIds(ids);
	}
}
