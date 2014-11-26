package com.ttd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttd.dao.BaseDao;
import com.ttd.dao.DistrictsDao;
import com.ttd.entity.Districts;
import com.ttd.service.DistrictsService;

/**
 * @author gaocy
 * 
 */
@Service
public class DistrictsServiceImpl extends BaseServiceImpl<Districts, Integer>
        implements DistrictsService {

	
	@Autowired
	private DistrictsDao districtsDao;
	
	@Override
	protected BaseDao<Districts, Integer> getDao() {
		return districtsDao;
	}

	@Override
	public List<Districts> getBy(Integer parentId) {
		return districtsDao.getBy(parentId);
		//return null;
	}


}
