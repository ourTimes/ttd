/*
 * Class: BaseDaoImpl
 * Description:持久层基础接口实现类
 * Version: 1.0
 */
package com.ttd.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import com.ttd.dao.BaseDao;
import com.ttd.model.Page;
import com.ttd.util.ReflectionUtils;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * @author gao
 * 
 */
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	public static final String CGLIB_PROXY_TAG = "$$EnhancerByCGLIB$$";
	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> clazz;
	protected Logger logger;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		// 根据反射获取T的Class对象
		Type genericSuperClass = getClass().getGenericSuperclass();

		// Get the generic super class of the super class if it's a cglib proxy
		if (getClass().getName().contains(CGLIB_PROXY_TAG)) {
			genericSuperClass = getClass().getSuperclass()
					.getGenericSuperclass();
		}
		this.clazz = (Class<T>) ((ParameterizedType) genericSuperClass)
				.getActualTypeArguments()[0];
		logger = LoggerFactory.getLogger(clazz);
	}

	/**
	 * 取得对象的主键名.
	 */
	private String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(clazz);
		return meta.getIdentifierPropertyName();
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object... values)
			throws DataAccessException {
		Assert.hasText(hql);
		Query q = createQuery(hql, values);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> paramMap)
			throws DataAccessException {
		Assert.hasText(hql);
		Query q = createQuery(hql, paramMap);
		return q.list();
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 * 
	 *         modify By Gaohuanquan 修改参数 paramMap的泛型将 Map<String, Object>
	 *         paramMap改为Map<String, ?> paramMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(Page<T> page, String hql, Map<String, ?> paramMap) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, paramMap);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, paramMap);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按SQL分页查询.
	 * 
	 * @param page
	 *            分页参数
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPageBySql(final Page page, final String sql,
			final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuerySQL(sql, values);

		if (page.isAutoCount()) {
			long totalCount = countSqlResult(sql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	// 重载方法 返回Map 对象集合
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPageBySql(final Page page, final String sql,
			boolean isReturnMap, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuerySQL(sql, values);

		if (page.isAutoCount()) {
			long totalCount = countSqlResult(sql, values);
			page.setTotalCount(totalCount);
		}
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public Query createQuerySQL(final String sql, final Map<String, ?> paramMap) {
		Assert.hasText(sql, "sql不能为空");
		Query query = getSession().createSQLQuery(sql);
		if (paramMap != null) {
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = paramMap.get(key);
				if (value instanceof Object[]) {
					query.setParameterList(key, (Object[]) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		return query;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param sql
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return
	 */
	public Query createQuerySQL(final String sql, final Object... values) {
		Assert.hasText(sql, "sql不能为空");
		Query query = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 执行count查询获得本次sql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 */
	protected long countSqlResult(final String sql, final Object... values) {
		String countSql = prepareCountSQL(sql);

		try {
			long count = (Integer) createQuerySQL(countSql, values)
					.uniqueResult();
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:"
					+ countSql, e);
		}
	}

	/**
	 * 执行count查询获得本次sql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 */
	protected long countSqlResult(final String sql,
			final Map<String, ?> paramMap) {
		String countSql = prepareCountSQL(sql);
		try {
			long count = (Integer) createQuerySQL(countSql, paramMap)
					.uniqueResult();
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:"
					+ countSql, e);
		}
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * modify By Gaohuanquan 修改参数 paramMap的泛型将 Map<String, Object>
	 * paramMap改为Map<String, ?> paramMap 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param paramMap
	 *            过滤条件
	 */
	public Query createQuery(final String queryString,
			final Map<String, ?> paramMap) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (paramMap != null) {
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = paramMap.get(key);
				if (value instanceof Object[]) {
					query.setParameterList(key, (Object[]) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		return query;
	}

	/**
	 * 执行count查询获得本次SQL查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);
		try {
			Long count = (Long) createQuery(countHql, values).uniqueResult();
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * modify By Gaohuanquan 修改参数 paramMap的泛型将 Map<String, Object>
	 * paramMap改为Map<String, ?> paramMap 执行count查询获得本次SQL查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql,
			final Map<String, ?> paramMap) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = (Long) createQuery(countHql, paramMap).uniqueResult();
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	private String prepareCountSQL(String orgSql) {
		String countSql = "select count(*) from (" + orgSql + ") tt";
		return countSql;
	}

	/**
	 * 按SQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueSQL(final String sql, final Object... values) {
		return (T) createQuerySQL(sql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final String hql, final Object... values) {
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Object findUniqueObject(final String hql, final Object... values) {
		return createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param paramMap
	 *            参数
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final String hql, final Map<String, Object> paramMap) {
		return (T) createQuery(hql, paramMap).uniqueResult();
	}

	/**
	 * 设置分页参数到Query对象
	 */
	protected Query setPageParameterToQuery(final Query q, final Page<?> page) {

		/**
		 * 不分页则直接返回
		 */
		if (!page.isPaging())
			return q;
		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	public void clear() throws DataAccessException {
		getSession().clear();
	}

	public void create(T entity) throws DataAccessException {
		getSession().save(entity);
	}

	public void createOrUpdate(T entity) throws DataAccessException {
		getSession().saveOrUpdate(entity);
	}

	public void delete(T entity) throws DataAccessException {
		getSession().delete(entity);
	}

	public void deleteById(PK id) throws DataAccessException {
		delete(get(id));
	}

	public void evit(T entity) throws DataAccessException {
		getSession().evict(entity);
	}

	/**
	 * 执行本地sql语句获得标量数值列表 返回Object数组
	 */
	@SuppressWarnings("rawtypes")
	public List executeNativeSql(String sql) throws DataAccessException {
		return getSession().createSQLQuery(sql).list();
	}

	/**
	 * 执行本地sql语句
	 */
	public int executeNativeSqlUpdate(String sql) throws DataAccessException {
		return getSession().createSQLQuery(sql).executeUpdate();
	}

	public void executeUpdate(String hql) throws DataAccessException {
		getSession().createQuery(hql).executeUpdate();
	}

	/**
	 * 执行本地sql语句获得标量数值列表 返回实体对象
	 */
	@SuppressWarnings("unchecked")
	public List<T> executeNativeSqlToClass(String sql)
			throws DataAccessException {
		return getSession().createSQLQuery(sql).addEntity(this.clazz).list();
	}

	/**
	 * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(clazz);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public void flush() throws DataAccessException {
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) throws DataAccessException {
		return (T) getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean includeSoftDeletedEntity)
			throws DataAccessException {
		Criteria crit = getSession().createCriteria(clazz);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (includeSoftDeletedEntity) {
			// 忽略删除标志位，返回所有结果
			return crit.list();
		} else {
			// 检查是否包含deleteFlag属性
			Field field = ReflectionUtils.getDeclaredField(clazz,
					BaseDao.FIELD_NAME_DELETE_FLAG);
			if (field == null) {
				// 不存在标志位，返回所有接口
				return crit.list();
			} else {
				return crit.add(
						Restrictions.eqOrIsNull(BaseDao.FIELD_NAME_DELETE_FLAG,
								false)).list();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean includeSoftDeletedEntity, String orderBy,
			boolean isAsc) throws DataAccessException {
		Assert.hasText(orderBy);
		Criteria crit = getSession().createCriteria(clazz);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (!includeSoftDeletedEntity) {
			// 检查是否包含deleteFlag属性
			Field field = ReflectionUtils.getDeclaredField(clazz,
					BaseDao.FIELD_NAME_DELETE_FLAG);
			if (field != null) {
				// 存在标志位
				crit.add(Restrictions.eqOrIsNull(
						BaseDao.FIELD_NAME_DELETE_FLAG, false));
			}
		}
		if (isAsc)
			return crit.addOrder(Order.asc(orderBy)).list();
		else
			return crit.addOrder(Order.desc(orderBy)).list();
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) throws DataAccessException {
		return (T) getSession().load(clazz, id);
	}

	public void merge(T entity) throws DataAccessException {
		getSession().merge(entity);
	}

	public void update(T entity) throws DataAccessException {
		getSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	public Page<T> findPageBySQL(Page<T> page, String sql, Object... values)
			throws DataAccessException {
		return findPageBySql(page, sql, values);
	}

	@SuppressWarnings("unchecked")
	public Page<T> findPageBySQL(Page<T> page, String sql, boolean isReturnMap,
			Object... values) throws DataAccessException {
		return findPageBySql(page, sql, isReturnMap, values);
	}

	@Override
	public void create(Collection<T> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for (T e = null; it.hasNext();) {
				e = it.next();
				create(e);
			}
		}

	}

	@Override
	public void delete(Collection<T> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for (T e = null; it.hasNext();) {
				e = it.next();
				delete(e);
			}
		}

	}

	@Override
	public void merge(Collection<T> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for (T e = null; it.hasNext();) {
				e = it.next();
				merge(e);
			}
		}

	}

	@Override
	public void update(Collection<T> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for (T e = null; it.hasNext();) {
				e = it.next();
				update(e);
			}
		}

	}

	@Override
	public void createOrUpdate(Collection<T> entities)
			throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for (T e = null; it.hasNext();) {
				e = it.next();
				createOrUpdate(e);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<PK, T> get(Collection<PK> ids) throws DataAccessException {
		List<T> list = createCriteria(Restrictions.in(getIdName(), ids)).list();
		Map<PK, T> result = new HashMap<PK, T>();
		for (T obj : list) {
			result.put((PK) getSession().getIdentifier(obj), obj);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> findByIds(Collection<PK> ids)
			throws DataAccessException {
		if (ids == null || ids.size() == 0) {
			// 集合为空，结果为空
			return new ArrayList<T>();
		}
		List<T> list = createCriteria(Restrictions.in(getIdName(), ids)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<PK, T> getAllByMap(boolean includeSoftDeletedEntity)
			throws DataAccessException {
		List<T> list = getAll(includeSoftDeletedEntity);
		Map<PK, T> result = new HashMap<PK, T>();
		for (T t : list) {
			result.put((PK) getSession().getIdentifier(t), t);
		}
		return result;
	}

	@Override
	public List<?> findObjlist(String hql, Object... values)
			throws DataAccessException {
		Assert.hasText(hql);
		Query q = createQuery(hql, values);
		return q.list();
	}

	@Override
	public List<?> findObjlist(String hql, Map<String, Object> param)
			throws DataAccessException {
		Assert.hasText(hql);
		Query q = createQuery(hql, param);
		return q.list();
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<?> findPageObject(Page<?> page, String queryStr, boolean isHql,
			Map<String, ?> param) throws DataAccessException {
		Assert.notNull(page, "page不能为空");
		Query q = null;
		if (isHql) {
			q = createQuery(queryStr, param);
		} else {
			q = createQuerySQL(queryStr, param);
		}
		if (page.isAutoCount()) {
			long totalCount = 0;
			if (isHql) {
				totalCount = countHqlResult(queryStr, param);
			} else {
				totalCount = countSqlResult(queryStr, param);
			}
			page.setTotalCount(totalCount);
		}
		setPageParameterToQuery(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 获得主键
	 * 
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PK getPK(T t) {
		return (PK) getSession().getIdentifier(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<PK, T> findMapByIds(Collection<PK> ids)
			throws DataAccessException {
		List<T> list = createCriteria(Restrictions.in(getIdName(), ids)).list();
		Map<PK, T> result = new HashMap<PK, T>();
		for (T t : list) {
			result.put((PK) getSession().getIdentifier(t), t);
		}
		return result;
	}

	@Override
	public List<?> findObjBySQL(String sql, Map<String,?> param)
			throws DataAccessException {
		Assert.hasText(sql);
		Query q = createQuerySQL(sql, param);
		return q.list();
	}

	@Override
	public List<?> findAllObject(Class<?> clazz,
			boolean includeSoftDeletedEntity) {
		Criteria crit = getSession().createCriteria(clazz);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (includeSoftDeletedEntity) {
			// 忽略删除标志位，返回所有结果
			return crit.list();
		} else {
			// 检查是否包含deleteFlag属性
			Field field = ReflectionUtils.getDeclaredField(clazz,
					BaseDao.FIELD_NAME_DELETE_FLAG);
			if (field == null) {
				// 不存在标志位，返回所有接口
				return crit.list();
			} else {
				return crit.add(
						Restrictions.eqOrIsNull(BaseDao.FIELD_NAME_DELETE_FLAG,
								false)).list();
			}
		}
	}

	@Override
	public Object findObjectById(Long key,Class<?> clazz) {
		return  getSession().load(clazz, key);
	}
}
