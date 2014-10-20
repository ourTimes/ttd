package com.ttd.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ttd.dao.RoleDao;


@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:spring-context.xml"})
public class DaoTest {
	
	@Autowired
	private RoleDao roleDao;
	@Transactional
	@Test
	public void test() {
		
		System.out.println(roleDao.get(1010002l).getName());
	}
	
}
