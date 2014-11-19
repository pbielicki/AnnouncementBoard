package com.bielu.annoboard.security;

import static com.opensymphony.xwork2.ActionContext.getContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bielu.annoboard.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -7705833092161980874L;
	private static final Log LOG = LogFactory.getLog(AuthenticationInterceptor.class);

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation inv) throws Exception {
		if (getContext().getSession() == null || getContext().getSession().get(User.class.getName()) == null) {
			LOG.error("Authenticated User could not be found in the HTTP session context under ["
					+ User.class.getName() + "] key.");
			
			throw new AuthenticationException("Authenticated User could not be found in the HTTP session context.");
		}

		return inv.invoke();
	}
}
