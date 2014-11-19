package com.bielu.annoboard.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.bielu.annoboard.dao.DaoException;
import com.bielu.annoboard.dao.UserDao;
import com.bielu.annoboard.domain.User;

public class UserDaoImpl extends DaoImpl<User> implements UserDao {

	private static final long serialVersionUID = -1881859106407240526L;
	private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

	@Override
	protected Class<User> getPersistentClass() {
		return User.class;
	}

	@SuppressWarnings("unchecked")
	public User findByUsernameAndPassword(String username, String password) {
		Object[] fields = new Object[] {username, password};
		String query = String.format("from %s as u where u.username = ? and u.password = ?", getPersistentClassName());
		List<User> list = getHibernateTemplate().find(query, fields);
		if (list.size() != 1) {
			String err = String.format("Could not find user [%s] with given password digest [%s]", username, password);
			LOG.debug(err);
			throw new DaoException(err);
		}
		
		return list.get(0);
	}
}
