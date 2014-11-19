package com.bielu.annoboard.action.admin;

import static com.bielu.util.junit.TestUtil.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.common.BaseActionTest;
import com.bielu.annoboard.dao.DaoException;
import com.bielu.annoboard.dao.UserDao;
import com.bielu.annoboard.domain.User;
import com.bielu.util.junit.Action;
import com.opensymphony.xwork2.ActionContext;

public class LogInActionTest extends BaseActionTest {

	@Autowired
	private LogInAction action;
	
	@Autowired
	private SignUpAction signUpAction;
	
	@Autowired
	private UserDao userDao;
	
	@After
	@Transactional
	public void tearDown() {
		userDao.deleteAll();
	}
	
	@Test
	public void testExecute() {
		User user = signUpUser();
		
		action.setUsername("testuser");
		action.setPassword("tespassword");

		assertEquals("success", action.execute());
		assertEquals(user, ActionContext.getContext().getSession().get(User.class.getName()));
	}


	@Test
	public void testExecuteInvalidPassword() {
		signUpUser();
		
		action.setUsername("testuser");
		action.setPassword("invalid");

		assertThrows(DaoException.class, new Action() {
			public void run() throws Exception {
				action.execute();
			}
		});
		assertNull(ActionContext.getContext().getSession().get("user"));
	}

	@Test
	public void testExecuteInvalidUsername() {
		signUpUser();
		
		action.setUsername("invalid");
		action.setPassword("tespassword");

		assertThrows(DaoException.class, new Action() {
			public void run() throws Exception {
				action.execute();
			}
		});
		assertNull(ActionContext.getContext().getSession().get("user"));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testExecuteNonExistingUsernameAndPassword() {
		action.setUsername("invalid");
		action.setPassword("invalid");
		ActionContext.getContext().setSession(new HashMap());

		assertThrows(DaoException.class, new Action() {
			public void run() throws Exception {
				action.execute();
			}
		});
		assertNull(ActionContext.getContext().getSession().get("user"));
	}
	
	@Test
	public void testPasswordField() {
		assertThrows(NullPointerException.class, new Action() {
			@Override
			public void run() throws Exception {
				action.setPassword(null);		
			}
		});
		
		action.setPassword("password");
		assertEquals("password", action.getPassword());
	}

	@SuppressWarnings("unchecked")
	private User signUpUser() {
		User user = new User();
		user.setUsername("testuser");
		user.setEmail("testuser@email.com");
		signUpAction.setPassword("tespassword");
		signUpAction.setUser(user);
		assertEquals("success", signUpAction.execute());
		ActionContext.getContext().setSession(new HashMap());
		return user;
	}
}
