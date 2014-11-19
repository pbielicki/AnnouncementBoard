package com.bielu.annoboard.security;

public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 3466418303600733460L;

	public AuthenticationException(String string) {
		super(string);
	}
}
