package com.ttd.dao.impl;

import java.util.List;

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
		// TODO Auto-generated method stub
		return this.getSession().createCriteria(Districts.class)
				.add(Restrictions.eqOrIsNull("parent_id", parentId)).list();
		//return null;
	}

}
