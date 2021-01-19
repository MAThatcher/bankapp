package dev.thatcher.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.thatcher.app.JDBCconnection;
import dev.thatcher.entity.UserAccount;

public class UserCrudImpl implements UserCrud {

	public static Connection conn = JDBCconnection.getConnection();

	public UserAccount create(UserAccount t) {
		try {
			String sql = "CALL add_user(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			int superuser = 0;
			if (t.isSuperUser()) {
				superuser = 1;
			}
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, String.valueOf(superuser));
			ps.executeQuery();
		} catch (SQLException e) {

		}
		return null;
	}

	public UserAccount getById(int id) {
		try {
			String sql = "SELECT * FROM users WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();

			UserAccount newUser = new UserAccount();
			if (rs.next()) {
				newUser.setId(rs.getInt("user_id"));
				newUser.setPassword(rs.getString("user_password"));
				newUser.setUsername(rs.getString("user_username"));
				newUser.setSuperUser(rs.getBoolean("user_superuser"));
				return newUser;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UserAccount update(UserAccount t) {
		try {
			String sql = "UPDATE users SET user_username = ?, user_password = ?, user_superuser = ? WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			int superuser = 0;
			if (t.isSuperUser()) {
				superuser=1;
			}
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, Integer.toString(superuser));
			ps.setString(4, Integer.toString(t.getId()));
			ps.executeQuery();
			return t;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM users WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<UserAccount> getAll() {
		try {
			String sql = "SELECT * FROM users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<UserAccount> output = new ArrayList<UserAccount>();
			while (rs.next()) {
				UserAccount newUser = new UserAccount();
				newUser.setId(rs.getInt("user_id"));
				newUser.setPassword(rs.getString("user_password"));
				newUser.setUsername(rs.getString("user_username"));
				newUser.setSuperUser(rs.getBoolean("user_superuser"));
				output.add(newUser);			
			}
			return output;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteAll(UserAccount user) {
		try {
			String sql = "DELETE FROM users where user_id != ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(user.getId()));
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserAccount getByUsername(String username) {
		try {
			String sql = "SELECT * FROM users WHERE user_username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				UserAccount output = new UserAccount();
				output.setId(rs.getInt("user_id"));
				output.setPassword(rs.getString("user_password"));
				output.setUsername(rs.getString("user_username"));
				output.setSuperUser(rs.getBoolean("user_superuser"));
				return output;
			}
			else {
				return null;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}