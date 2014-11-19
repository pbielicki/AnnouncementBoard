package com.bielu.annoboard.dao;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

public final class DaoUtil {

	private DaoUtil() {
	}

	public static DaoIntegrityException translateException(DataIntegrityViolationException e) {
		if (e.getCause() != null) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return new DaoDuplicateExcpetion(e);
			}
		}
		return new DaoIntegrityException(e);
	}
}
