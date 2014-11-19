package com.bielu.annoboard.action.admin;

import static com.bielu.util.junit.TestUtil.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.common.BaseActionTest;
import com.bielu.annoboard.dao.DaoDuplicateExcpetion;
import com.bielu.annoboard.dao.DaoIntegrityException;
import com.bielu.annoboard.dao.UserDao;
import com.bielu.annoboard.domain.User;
import com.bielu.util.junit.Action;
import com.bielu.util.junit.TestUtil;
import com.opensymphony.xwork2.ActionSupport;

public class SignUpActionTest extends BaseActionTest {

	@Autowired
	private SignUpAction action;
	
	@Autowired
	private UserDao userDao;

	@After
	@Transactional
	public void tearDown() {
		userDao.deleteAll();
	}
	
	@Test
	public void testInitUser() {
		assertNull(action.getUser());
		assertEquals("input", action.init());
		assertNotNull(action.getUser());
	}
	
	@Test
	public void testExecute() {
		createUser(1);
		assertEquals(DigestUtils.shaHex("password1"), action.getUser().getPassword());

		TestUtil.assertThrows(DaoDuplicateExcpetion.class, new Action() {
			public void run() throws Exception {
				createUser(1);
			}
		});
	}

	private User createUser(int id) {
		User user = new User();
		user.setUsername("user" + id);
		user.setFirstName("FirstName" + id);
		user.setLastName("LastName" + id);
		user.setEmail("user" + id + "@email.com");

		action.setPassword("password" + id);
		action.setUser(user);
		assertEquals(ActionSupport.SUCCESS, action.execute());
		
		return action.getUser();
	}

	@Test
	public void testCreateUserWithNullFields() {
		User user = new User();
		user.setFirstName("FirstName");
		user.setLastName("LastName");
		user.setEmail("user@email.com");
		action.setPassword("");
		action.setUser(user);
		
		TestUtil.assertThrows(DaoIntegrityException.class, new Action() {
			public void run() throws Exception {
				action.execute();
			}
		});

		user = new User();
		user.setUsername("user");
		user.setFirstName("FirstName");
		user.setLastName("LastName");
		action.setPassword("");
		action.setUser(user);

		TestUtil.assertThrows(DaoIntegrityException.class, new Action() {
			public void run() throws Exception {
				action.execute();
			}
		});

		user = new User();
		user.setUsername("user");
		user.setFirstName("FirstName");
		user.setLastName("LastName");
		user.setEmail("user@email.com");
		action.setPassword("");
		action.setUser(user);

		assertEquals(ActionSupport.SUCCESS, action.execute());
	}
	
	@Test
	public void testPasswordFields() {
		assertThrows(NullPointerException.class, new Action() {
			@Override
			public void run() throws Exception {
				action.setPassword(null);		
			}
		});
		
		assertThrows(NullPointerException.class, new Action() {
			@Override
			public void run() throws Exception {
				action.setConfirmPassword(null);		
			}
		});
		
		action.setPassword("password");
		assertEquals("password", action.getPassword());
		action.setConfirmPassword("confirmPassword");
		assertEquals("confirmPassword", action.getConfirmPassword());
	}
}
