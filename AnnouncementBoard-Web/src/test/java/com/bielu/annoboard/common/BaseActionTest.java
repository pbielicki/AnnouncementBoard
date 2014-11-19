package com.bielu.annoboard.common;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/spring/applicationContext*.xml", 
		"classpath:/spring/context-hibernate-test.xml" 
		})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public abstract class BaseActionTest {
}