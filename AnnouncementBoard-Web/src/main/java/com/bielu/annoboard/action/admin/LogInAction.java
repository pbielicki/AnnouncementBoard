package com.bielu.annoboard.action.admin;

import static com.opensymphony.xwork2.ActionContext.getContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.dao.DaoException;
import com.bielu.annoboard.dao.UserDao;
import com.bielu.annoboard.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

public class LogInAction extends ActionSupport {

	private static final long serialVersionUID = -4515219541126617675L;

	private char[] password;
	private String username;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String execute() {
		try {
			User user = userDao.findByUsernameAndPassword(username, DigestUtils.shaHex(String.valueOf(password)));
			user.setPassword("");
			password = null;
			getContext().getSession().put(User.class.getName(), user);
			
			return SUCCESS;
		} catch (DaoException e) {
			addActionError(getText("error.authentication.failed"));
			throw e;
		}
	}
	
	@RequiredStringValidator(message = "input.empty.username", key = "input.empty.username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@RequiredStringValidator(message = "input.empty.password", key = "input.empty.password")
	public String getPassword() {
		return String.valueOf(password);
	}

	public void setPassword(String password) {
		this.password = password.toCharArray();
	}
}
