package com.bielu.annoboard.action.admin;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.dao.DaoDuplicateExcpetion;
import com.bielu.annoboard.dao.UserDao;
import com.bielu.annoboard.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

public class SignUpAction extends ActionSupport {

	private static final long serialVersionUID = -297307302689818253L;

	private String confirmEmail;
	private char[] confirmPassword;
	private char[] password;
	private User user;
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public String execute() {
		user.setPassword(DigestUtils.shaHex(String.valueOf(password)));
		try {
			userDao.saveOrUpdate(user);
		} catch (DaoDuplicateExcpetion e) {
			addActionError(getText("error.userAlreadyExists", new String[] { user.getUsername() }));
			addFieldError("user.username", getText("error.username.alreadyExists"));
			throw e;
		}
		
		return SUCCESS;
	}
	
	public String init() {
		user = new User();
		return INPUT;
	}

	@FieldExpressionValidator(
			expression = "user.email.equals(confirmEmail)", 
			message = "validation.notSame.email", 
			key = "validation.notSame.email"
	)
	public String getConfirmEmail() {
		return confirmEmail;
	}

	public String getConfirmPassword() {
		return String.valueOf(confirmPassword);
	}

	@RequiredStringValidator(message = "validation.empty.password", key = "validation.empty.password")
	@StringLengthFieldValidator(
			minLength = "6",
			maxLength = "25",
			trim = true,
			message = "validation.length.password",
			key = "validation.length.password"
	)
	@FieldExpressionValidator(
			expression = "password.equals(confirmPassword)", 
			message = "validation.notSame.password", 
			key = "validation.notSame.password"
	)
	public String getPassword() {
		return String.valueOf(password);
	}

	@VisitorFieldValidator(message = "")
	public User getUser() {
		return user;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public void setConfirmPassword(String confimPassword) {
		this.confirmPassword = confimPassword.toCharArray();
	}

	public void setPassword(String password) {
		this.password = password.toCharArray();
	}

	public void setUser(User user) {
		this.user = user;
	}
}
