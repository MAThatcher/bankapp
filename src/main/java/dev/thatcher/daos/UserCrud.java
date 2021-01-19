package dev.thatcher.daos;

import dev.thatcher.entity.UserAccount;

public interface UserCrud extends DAO<UserAccount>{
	boolean deleteAll(UserAccount user);
	UserAccount getByUsername(String username);
}
