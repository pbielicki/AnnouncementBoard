package com.bielu.annoboard.dao;


public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -2168879407618771776L;

	public DaoException(Throwable e) {
		super(e);
	}

	public DaoException(String string) {
		super(string);
	}
}
