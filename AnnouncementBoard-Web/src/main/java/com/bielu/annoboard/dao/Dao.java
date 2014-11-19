package com.bielu.annoboard.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> extends Serializable {

	List<T> loadAll();
	
	T findById(long id);
	
	void saveOrUpdate(T object) throws DaoIntegrityException;
	
	void deleteAll();
}
