package com.bielu.annoboard.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

public class DaoUtilTest {

	@Test
	public void testTranslateException() {
		final DataIntegrityViolationException ex = new DataIntegrityViolationException("msg");
		assertEquals(DaoIntegrityException.class, DaoUtil.translateException(ex).getClass());

		final DataIntegrityViolationException ex2 = new DataIntegrityViolationException("msg",
				new IllegalAccessException("msg"));
		assertEquals(DaoIntegrityException.class, DaoUtil.translateException(ex2).getClass());

		final DataIntegrityViolationException ex1 = new DataIntegrityViolationException("msg",
				new ConstraintViolationException("msg", new SQLException(), "cst"));
		assertEquals(DaoDuplicateExcpetion.class, DaoUtil.translateException(ex1).getClass());
	}

}
