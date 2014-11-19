package com.bielu.annoboard.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LocationTest {

	@Test
	public void testToString() {
		Location o = new Location();
		
		String format = "(Location: id[%d] address[%s] city[%s] country[%s] lat[%f] lng[%f])";
		assertEquals(String.format(format, 0, null, null, null, 0.0, 0.0), o.toString());
		
		o.setAddress("some address");
		o.setId(1);
		assertEquals(String.format(format, 1, "some address", null, null, 0.0, 0.0), o.toString());

		o.setCity("city");
		o.setId(11);
		assertEquals(String.format(format, 11, "some address", "city", null, 0.0, 0.0), o.toString());

		o.setCountryCode("country");
		o.setId(112);
		assertEquals(String.format(format, 112, "some address", "city", "country", 0.0, 0.0), o.toString());

		o.setLatitude(12.3456);
		o.setLongitude(147.1098);
		o.setId(212);
		assertEquals(String.format(format, 212, "some address", "city", "country", 12.3456, 147.1098), o.toString());
	}
}
