package com.bielu.struts2.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EmptyToNullStringConverterTest {

	@Test
	public void testConvertFromStringMapStringArrayClass() {
		EmptyToNullStringConverter c = new EmptyToNullStringConverter();
		assertEquals("string", c.convertFromString(null, new String[] {"string"}, Object.class));
	}

	@Test
	public void testConvertToStringMapObject() {
		EmptyToNullStringConverter c = new EmptyToNullStringConverter();
		assertNull(c.convertToString(null, new String[] {""}));
		assertNull(c.convertToString(null, ""));
		assertNull(c.convertToString(null, null));
		assertNull(c.convertToString(null, new String[0]));
		
		assertEquals("string", c.convertToString(null, new String[] {"string"}));
		assertEquals("String", c.convertToString(null, "String"));
		assertEquals("", c.convertToString(null, new Object()));
	}

}
