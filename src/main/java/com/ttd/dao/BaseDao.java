/*
 * Class: BaseDao
 * Description:持久层基础接口
 * Version: 1.0
 */
package com.ttd.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

import com.ttd.model.Page;

/**
 * @author gao
 * 
 */
public interface BaseDao<T, PK extends Serializable> {
	/**
	 * 删除标志位的属性名称
	 */
	public static final String FIELD_NAME_DELETE_FLAG = "deleteFlag";
	/**
	 * 清除所有对象缓存
	 */
	public void clear() throws DataAccessException;

	/**
	 * 持久化多个实体。
	 * 
	 * @param entities
	 *            ：处于临时状态的实体的集合。
	 * @throws DataAccessException
	 */
	void create(Collection<T> entities) throws DataAccessException;

	/**
	 * 持久化一个实体。
	 * 
	 * @param entity
	 *            ：处于临时状态的实体。
	 * @throws DataAccessException
	 */
	void create(T entity) throws DataAccessException;

	/**
	 * 持久化或者更新多个实体。
	 * 
	 * @param entities
	 *            ：处于临时或者持久化状态的实体的集合。
	 * @throws DataAccessException
	 */
	void createOrUpdate(Collection<T> entities) throws DataAccessException;

	/**
	 * 持久化或者更新实体。
	 * 
	 * @param entity
	 *            ：处于临时或者持久化状态的实体。
	 * @throws DataAccessException
	 */
	void createOrUpdate(T entity) throws DataAccessException;

	/**
	 * 删除多个持久化的实体。
	 * 
	 * @param entities
	 *            ：处于持久化状态的实体的集合。
	 * @throws DataAccessException
	 */
	void delete(Collection<T> entities) throws DataAccessException;

	/**
	 * 删除一个持久化的实体。
	 * 
	 * @param entity
	 *            ：处于持久化状态的实体。
	 * @throws DataAccessException
	 */
	void delete(T entity) throws DataAccessException;

	/**
	 * 根据ID移除对象.
	 */
	public void deleteById(PK id) throws DataAccessException;

	/**
	 * 消除与 Hibernate Session 的关联
	 */
	public void evit(T entity) throws DataAccessException;

	/**
	 * 执行本地sql语句获得标量数值列表 返回Object数组
	 */
	@SuppressWarnings("rawtypes")
	public List executeNativeSql(String sql) throws DataAccessException;

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @throws DataAccessException
	 */
	public int executeNativeSqlUpdate(String sql) throws DataAccessException;

	/**
	 * 执行hql语句
	 * 
	 * @param hql
	 * @throws DataAccessException
	 */
	public void executeUpdate(String hql) throws DataAccessException;

	/**
	 * 执行本地sql语句获得标量数值列表 返回实体对象
	 */
	public List<T> executeNativeSqlToClass(String sql)
			throws DataAccessException;

	/**
	 * 根据hql查询
	 */
	@Deprecated
	public List<T> find(String hql, Object... values)
			throws DataAccessException;

	/**
	 * 根据hql查询
	 */
	public List<T> find(String hql, Map<String, Object> paramMap)
			throws DataAccessException;

	public void flush();

	/**
	 * 根据ID获取对象,如果对象不存在，返回 null
	 */
	public T get(PK id) throws DataAccessException;

	/**
	 * 根据ID集合获取对象MAP,ID作为KEY，如果对象不存在，KEY对应的VALUE为null
	 */
	public Map<PK, T> get(Collection<PK> ids) throws DataAccessException;

	/**
	 * 获取全部对象
	 * @param includeSoftDeletedEntity 包含软删除的entity，若为true，则将包含已软删除的所有信息
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> getAll(boolean includeSoftDeletedEntity) throws DataAccessException;

	/**
	 * 通过键值对的形式返回全部的查询结果
	 * @param includeSoftDeletedEntity 包含软删除的entity，若为true，则将包含已软删除的所有信息
	 * @return
	 * @throws DataAccessException
	 */
	public Map<PK, T> getAllByMap(boolean includeSoftDeletedEntity) throws DataAccessException;

	/**
	 * 请使用findUnique(final String hql, final Map paramMap)代替
	 * 
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@Deprecated
	public T findUnique(final String hql, final Object... values);

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param paramMap
	 *            参数
	 */
	public T findUnique(final String hql, final Map<String, Object> paramMap);

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@Deprecated
	public Object findUniqueObject(final String hql, final Object... values);

	/**
	 * 查询所有对象
	 * @param includeSoftDeletedEntity 是否包含已软删除的对象
	 * @param orderBy
	 * @param isAsc
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> getAll(boolean includeSoftDeletedEntity,String orderBy, boolean isAsc)
			throws DataAccessException;

	/**
	 * 根据ID获取对象，如果对象不存在，抛出异常.
	 */
	public T load(PK id) throws DataAccessException;

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString,
			final Map<String, ?> values);


	/**
	 * 保存处于游离状态的多个实体，更新后实体对象仍然处于游离状态。
	 * 
	 * @param entities
	 *            ：处于游离状态的实体的集合。
	 * @throws DataAccessException
	 */
	void merge(Collection<T> entities) throws DataAccessException;

	/**
	 * 更新处于游离状态的实体，更新后实体对象仍然处于游离状态。
	 * 
	 * @param entity
	 *            ：处于游离状态的实体。
	 * @throws DataAccessException
	 */
	void merge(T entity) throws DataAccessException;

	/**
	 * <b>请使用findPage(Page page, String hql, Map paramMap)代替</b>
	 * 
	 * 分页查询
	 * 
	 * @param page
	 *            ：分页对象
	 * @param hql
	 *            ：hql语句
	 * @param values
	 *            ：参数
	 * @return page：对象
	 * @throws DataAccessException
	 */
	@Deprecated
	Page<T> findPage(Page<T> page, String hql, Object... values)
			throws DataAccessException;

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            ：分页对象
	 * @param hql
	 *            ：hql语句
	 * @param values
	 *            ：与hql对应的参数
	 * @return page：对象
	 * @throws DataAccessException
	 */
	Page<T> findPage(Page<T> page, String hql, Map<String, ?> paramMap)
			throws DataAccessException;

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            ：分页对象
	 * @param sql
	 *            ：hql语句
	 * @param values
	 *            ：参数值
	 * @return page：对象
	 * @throws DataAccessException
	 */
	@Deprecated
	Page<T> findPageBySQL(Page<T> page, String sql, Object... values)
			throws DataAccessException;

	/**
	 * 更新多个实体。
	 * 
	 * @param entities
	 *            ：处于持久化状态的实体的集合。
	 * @throws DataAccessException
	 */
	void update(Collection<T> entities) throws DataAccessException;

	/**
	 * 更新实体。
	 * 
	 * @param entity
	 *            ：处于持久化状态的实体。
	 * @throws DataAccessException
	 */
	void update(T entity) throws DataAccessException;

	/**
	 * @author GaoHuanquan 新添加 根据hql查询
	 */
	@Deprecated
	public List<?> findObjlist(String hql, Object... values)
			throws DataAccessException;

	/**
	 * @author GaoHuanquan 新添加 分页形式查询对象列表
	 */
	public Page<?> findPageObject(Page<?> page, String hql,boolean isHql, Map<String,?> param)
			throws DataAccessException;

	/**
	 * 获得主键
	 * 
	 * @param t
	 * @return
	 */
	public PK getPK(T t);

	/**
	 * 根据ID集合查询对象
	 * @param ids
	 * @return
	 * @throws DataAccessException
	 */
	Collection<T> findByIds(Collection<PK> ids) throws DataAccessException;

	/**
	 * 查询对象列表
	 * @param hql
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<?> findObjlist(String hql, Map<String, Object> param) throws DataAccessException;

	/**
	 * 查询对象列表
	 * @param hql
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<?> findObjBySQL(String sql, Map param) throws DataAccessException;
	
	/**
	 * 根据id集合查询对象,通过键值对的形式返回全部的查询结果
	 * @param ids
	 * @return
	 */
	Map<PK, T> findMapByIds(Collection<PK> ids) throws DataAccessException;
	
	List<?> findAllObject(Class<?> clazz,boolean includeSoftDeletedEntity);
	
	Object  findObjectById(Long key,Class<?> clazz);
}
