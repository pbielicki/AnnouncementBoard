package com.bielu.annoboard.security;

import static com.bielu.util.junit.TestUtil.assertThrows;
import static com.opensymphony.xwork2.ActionContext.getContext;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.bielu.annoboard.domain.User;
import com.bielu.util.junit.Action;
import com.opensymphony.xwork2.ActionInvocation;

public class AuthenticationInterceptorTest {

	private AuthenticationInterceptor interceptor;
	private ActionInvocation inv;
	
	@Before
	public void setUp() {
		interceptor = new AuthenticationInterceptor();
		inv = createMock(ActionInvocation.class);
	}
	
	@Test
	public void testDestroy() {
		interceptor.destroy();
	}

	@Test
	public void testInit() {
		interceptor.init();
	}

	@Test
	public void testIntercept() throws Exception {
		assertThrows(AuthenticationException.class, new Action() {
			@Override
			public void run() throws Exception {
				interceptor.intercept(inv);
			}
		});
		
		Map<String, Object> session = new HashMap<String, Object>();
		getContext().setSession(session);
		assertThrows(AuthenticationException.class, new Action() {
			@Override
			public void run() throws Exception {
				interceptor.intercept(inv);
			}
		});

		session.put(User.class.getName(), new User());
		expect(inv.invoke()).andReturn("some value");
		
		replay(inv);
		assertEquals("some value", interceptor.intercept(inv));
		verify(inv);
	}

}
