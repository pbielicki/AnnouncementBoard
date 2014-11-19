package com.bielu.annoboard.domain;

import java.util.Calendar;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

public class User implements Identifiable {
	
	private static final long serialVersionUID = -200489561445453679L;

	private long id;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private Calendar creationDate;
	
	private String password;
	
	public User() {
		creationDate = Calendar.getInstance();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@RequiredStringValidator(message = "validation.empty.username", key = "validation.empty.username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@RequiredStringValidator(message = "validation.empty.email", key = "validation.empty.email")
	@EmailValidator(message = "validation.invalid.email", key = "validation.invalid.email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Calendar getCreationDate() {
		return (Calendar) creationDate.clone();
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		final User other = (User) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("(User: id[%d] username[%s] email[%s])", id, username, email);
	}

}	