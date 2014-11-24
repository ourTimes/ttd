package com.ttd.service;

import java.util.List;

import com.ttd.entity.Districts;

public interface DistrictsService extends BaseService<Districts, Integer> {
	
	/**
	 * 根据parentId获取直接下级
	 * @param parentId
	 * @return
	 */
	public List<Districts> getBy(Integer parentId);
	
}
