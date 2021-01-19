package dev.thatcher.services;

import java.util.List;

import dev.thatcher.daos.UserCrud;
import dev.thatcher.daos.UserCrudImpl;
import dev.thatcher.entity.UserAccount;

public class UserAccountServiceImpl implements UserAccountService {

	private static UserCrud crud = new UserCrudImpl();

	public UserAccount createAccount(String username, String password) {
		if (crud.getByUsername(username) != null) {
			System.out.println("Username Taken, Account not created");
			return null;
		}
		UserAccount newUserAccount = new UserAccount(username, password);
		crud.create(newUserAccount);
		System.out.println("Account Created");
		return newUserAccount;
	}

	public UserAccount createAccount(UserAccount userAccount) {
		if (crud.getByUsername(userAccount.getUsername()) != null) {
				System.out.println("Username Taken, Account not created");
				return null;
		}
		crud.create(userAccount);
		System.out.println("Account Created");
		return userAccount;
	}

	public UserAccount logIn(String username, String password) {
		UserAccount user = crud.getByUsername(username);
		if (user == null) {
			System.out.println("username or password inccorect");
			return null;
		}
		if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
			System.out.println("Logged in as " + user.getUsername());
			return user;
		}
		System.out.println("username or password inccorect");
		return null;
	}

	public UserAccount changeUsername(UserAccount userAccount, String username) {
		if (crud.getByUsername(username) == null) {
			userAccount.setUsername(username);
			System.out.println("User name Changed");
			return crud.update(userAccount);
		}
		System.out.println("Username taken. Action canceled");
		return null;
	}

	public UserAccount changePassword(UserAccount userAccount, String password) {
		userAccount.setPassword(password);
		System.out.println("Password Changed");
		return crud.update(userAccount);
	}

	public UserAccount makeSuperUser(UserAccount userAccount) {
		userAccount.setSuperUser(true);
		System.out.println("Permissions granted");
		return crud.update(userAccount);
	}

	public boolean deleteAccount(UserAccount userAccount) {
		return crud.delete(userAccount.getId());
	}

	// SUPER USER FUNCTIONS
	public UserAccount createAccount(UserAccount userAccount, String username, String password) {
		if (userAccount.isSuperUser()) {
			return this.createAccount(username, password);
		}
		System.out.println("Must be a super user");
		return null;
	}

	public List<UserAccount> viewAllUsers(UserAccount userAccount) {
		if (userAccount.isSuperUser()) {
			return crud.getAll();
		}
		System.out.println("Must be a super user");
		return null;
	}

	public UserAccount deleteAllUsers(UserAccount userAccount) {
		if (userAccount.isSuperUser()) {
			crud.deleteAll(userAccount);
			System.out.println("Users Deleted");
			return userAccount;
		}
		System.out.println("Must be a super user");
		return null;
	}

	// This method is never called in app.java
	public UserAccount updateUser(UserAccount userAccount) {
		if (userAccount.isSuperUser()) {
			crud.update(userAccount);
		}
		return userAccount;
	}
	
}
