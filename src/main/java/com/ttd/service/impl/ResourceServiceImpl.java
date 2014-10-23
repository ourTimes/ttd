/*
 * Class: ResourceServiceImpl
 * Description:资源服务接口实现类
 * Version: 1.0
 */
package com.ttd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttd.dao.BaseDao;
import com.ttd.dao.ResourceDao;
import com.ttd.entity.Resource;
import com.ttd.service.ResourceService;

/**
 * @author gao
 * 
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Long>
		implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Override
	protected BaseDao<Resource, Long> getDao() {
		return resourceDao;
	}

}
