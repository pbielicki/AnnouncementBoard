package com.bielu.annoboard.dao;

import org.springframework.dao.DataIntegrityViolationException;

public class DaoDuplicateExcpetion extends DaoIntegrityException {
	
	private static final long serialVersionUID = 3973147306170711303L;

	public DaoDuplicateExcpetion(DataIntegrityViolationException e) {
		super(e);
	}
}
