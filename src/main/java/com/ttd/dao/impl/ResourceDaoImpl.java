/*
 * Class: ResourceDaoImpl
 * Description:资源对象持久层实现类
 * Version: 1.0
 */
package com.ttd.dao.impl;

import org.springframework.stereotype.Component;

import com.ttd.dao.ResourceDao;
import com.ttd.entity.Resource;

/**
 * 
 * @author gao
 * 
 */
@Component
public class ResourceDaoImpl extends BaseDaoImpl<Resource, Long> implements
		ResourceDao {

}
