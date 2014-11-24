package com.ttd.dao;

import java.util.List;

import com.ttd.entity.Districts;

public interface DistrictsDao extends BaseDao<Districts, Integer> {

	/**
	 * 根据parentId获取直接下级
	 * @param parentId
	 * @return
	 */
	public List<Districts> getBy(Integer parentId);
	
}
