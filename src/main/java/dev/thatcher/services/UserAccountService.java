package dev.thatcher.services;

import java.util.List;

import dev.thatcher.entity.UserAccount;

public interface UserAccountService {
	
	UserAccount createAccount(UserAccount userAccount);
	UserAccount createAccount(String username, String password);
	UserAccount logIn(String username, String password);
	UserAccount changeUsername(UserAccount userAccount, String username);
	UserAccount changePassword(UserAccount userAccount, String password);
	UserAccount makeSuperUser(UserAccount userAccount);
	boolean deleteAccount(UserAccount userAccount);
	
	// functions for super users
	UserAccount createAccount(UserAccount userAccount, String username, String password);
	List<UserAccount> viewAllUsers(UserAccount userAccount);
	UserAccount deleteAllUsers(UserAccount userAccount);
	UserAccount updateUser(UserAccount userAccount);
}
