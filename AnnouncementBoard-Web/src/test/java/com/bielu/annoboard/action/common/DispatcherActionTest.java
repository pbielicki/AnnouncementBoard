package com.bielu.annoboard.action.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bielu.annoboard.common.BaseActionTest;

public class DispatcherActionTest extends BaseActionTest {

	@Autowired
	private DispatcherAction action;
	
	@Test
	public void testExecute() {
		assertEquals("success", action.execute());
		assertEquals("/main.jsp", action.getResult());
		
		action.setResult("other");
		assertEquals("success", action.execute());
		assertEquals("other", action.getResult());
	}
}
