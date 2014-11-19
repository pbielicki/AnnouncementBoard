package com.bielu.annoboard.dao;

import com.bielu.annoboard.domain.User;

public interface UserDao extends Dao<User> {

	User findByUsernameAndPassword(String username, String password);

}
