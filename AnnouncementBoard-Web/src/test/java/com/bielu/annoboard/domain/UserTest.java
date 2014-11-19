package com.bielu.annoboard.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class UserTest {

	@Test
	public void testToString() {
		User u = new User();
		
		String format = "(User: id[%d] username[%s] email[%s])";
		assertEquals(String.format(format, 0, null, null), u.toString());
		
		u.setUsername("username");
		u.setId(1);
		assertEquals(String.format(format, 1, "username", null), u.toString());

		u.setEmail("email");
		u.setId(11);
		assertEquals(String.format(format, 11, "username", "email"), u.toString());
	}
	
	@Test
	public void testEquals() {
		User u1 = new User();
		assertEquals(u1, u1);
		
		u1.setUsername("user1");
		assertEquals(u1, u1);

		User u2 = new User();
		assertFalse(u1.equals(u2));
		assertFalse(u2.equals(u1));
		
		u2.setUsername(u1.getUsername());
		assertEquals(u1, u2);
		assertEquals(u2, u1);
		
		User u3 = new User();
		u3.setUsername(u2.getUsername());
		assertEquals(u2, u3);
		assertEquals(u1, u3);
		assertEquals(u3, u1);
		
		assertFalse(u1.equals(null));
		assertFalse(u1.equals(new Object()));
	}


	@Test
	public void testHashCode() {
		User u1 = new User();
		User u2 = new User();
		
		assertEquals(u1.hashCode(), u2.hashCode());
		u1.setUsername("username");

		assertFalse(u1.hashCode() == u2.hashCode());

		u2.setUsername(u1.getUsername());
		assertEquals(u1.hashCode(), u2.hashCode());
	}

	@Test
	public void testEqualsAndHashCodeContract() {
		User u1 = new User();
		User u2 = new User();
		
		assertEquals(u1.hashCode(), u2.hashCode());
		assertEquals(u1, u2);
		u1.setUsername("username");

		assertFalse(u1.hashCode() == u2.hashCode());
		assertFalse(u1.equals(u2));

		u2.setUsername(u1.getUsername());
		assertEquals(u1.hashCode(), u2.hashCode());
		assertEquals(u1, u2);
	}

}
