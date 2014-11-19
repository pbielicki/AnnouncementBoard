package com.bielu.annoboard.dao.impl;

import com.bielu.annoboard.dao.LocationDao;
import com.bielu.annoboard.domain.Location;

public class LocationDaoImpl extends DaoImpl<Location> implements LocationDao {

	private static final long serialVersionUID = 1643881284753101480L;

	@Override
	protected Class<Location> getPersistentClass() {
		return Location.class;
	}
}
