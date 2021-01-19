package dev.thatcher.entity;

public class UserAccount {
	private int id = 0;
	private String username;
	private String password;
	private boolean superUser = false;

	public UserAccount() {
		super();
	}

	public UserAccount(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	//GETTERS AND SETTERS
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSuperUser() {
		return superUser;
	}

	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//END GETTERS AND SETTERS
	
	
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", username=" + username + ", password=" + password + ", superUser="
				+ superUser + "]";
	}

}