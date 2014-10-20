package com.ttd.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T> {
	
    @Autowired
    private SessionFactory sessionFactory;

    protected Class<T> entityClass;

    public BaseDao() {
        this.entityClass = getEntityClass();
    }
    
    protected abstract Class<T> getEntityClass();
    
    public T load(Serializable id) {
        if (null == id)return null;
        return (T) getSession().load(entityClass, id);
    }
    
    public T get(Serializable id){
        if (null == id)
            return null;
        return (T) getSession().get(entityClass, id);
    }
    
    public Serializable save(T entity) {
    	if (null == entity)return null;
        return getSession().save(entity);
    }
    
    public void saveOrUpdate(T entity) {
        if (null == entity)return;
        getSession().saveOrUpdate(entity);
    }

    protected final Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
