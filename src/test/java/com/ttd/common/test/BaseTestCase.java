package com.ttd.common.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring*.xml"})
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {

}