package com.ttd.dao.impl;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ttd.common.test.BaseTestCase;
import com.ttd.dao.DistrictsDao;

@Component
public class DistrictsDaoImplTest extends BaseTestCase {

	private DistrictsDao districtsDao;
	
	@Test
	public void getBy() {
		Assert.notNull(districtsDao);
	}

}
