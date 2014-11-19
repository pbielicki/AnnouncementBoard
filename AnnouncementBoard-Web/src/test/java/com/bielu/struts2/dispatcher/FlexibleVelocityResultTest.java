package com.bielu.struts2.dispatcher;

import static org.junit.Assert.*;

import org.junit.Test;

public class FlexibleVelocityResultTest {

	@Test
	public void testGetContentTypeString() {
		FlexibleVelocityResult res = new FlexibleVelocityResult();
		assertEquals("text/html", res.getContentType(null));
		
		res.setContentType("text/something");
		assertEquals("text/something", res.getContentType("123"));

		res.setContentType("text/xml");
		assertEquals("text/xml", res.getContentType(null));

		res.setContentType("application");
		assertEquals("application", res.getContentType("content"));
	}
}
