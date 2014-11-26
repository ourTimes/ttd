package com.ttd.dao.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.ttd.dao.DistrictsDao;
import com.ttd.entity.Districts;

@Component
public class DistrictsDaoImpl extends BaseDaoImpl<Districts, Integer> implements
		DistrictsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Districts> getBy(Integer parentId) {

		return this.getSession().createCriteria(Districts.class)
				.add(Restrictions.eq(Districts._parentId, parentId))
				.addOrder(Order.asc(Districts._id))
				.list();
	}

}
