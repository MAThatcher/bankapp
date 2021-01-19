package dev.thatcher.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.thatcher.app.JDBCconnection;
import dev.thatcher.entity.BankAccount;

public class BankCrudImpl implements BankCrud {

	public static Connection conn = JDBCconnection.getConnection();

	public BankAccount create(int userId) {
		try {
			String sql = "CALL add_bank_account(?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(userId));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				BankAccount b = new BankAccount();
				b.setBankAccountId(rs.getInt("bank_id"));
				b.setUserId(userId);
				return b;
			}

		} catch (SQLException e) {

		}
		return null;
	}

	public BankAccount create(BankAccount t) {
		try {
			String sql = "CALL add_bank_account(?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(t.getUserId()));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t.setUserId(rs.getInt("user_id"));
				System.out.println("Bank Account Created");
				return t;
			}

		} catch (SQLException e) {
			System.out.println("Action Failed");
		}
		return t;
	}

	public BankAccount getById(int id) {
		try {
			String sql = "SELECT * FROM bank_accounts WHERE bank_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();

			BankAccount output = new BankAccount();
			if (rs.next()) {
				output.setBalance(rs.getDouble("bank_balance"));
				output.setBankAccountId(rs.getInt("bank_id"));
				output.setUserId(rs.getInt("user_id"));
				return output;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BankAccount update(BankAccount account) {
		try {
			String sql = "UPDATE bank_accounts SET bank_balance = ? WHERE bank_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Double.toString(account.getBalance()));
			ps.setString(2, Integer.toString(account.getBankAccountId()));
			ps.executeQuery();
			return account;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BankAccount update(BankAccount account, double amount) {
		try {
			String sql = "call add_transaction(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(account.getBankAccountId()));
			ps.setString(2, Double.toString(amount));
			ps.executeQuery();
			return account;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete(int bankId) {
		try {
			String sql = "DELETE FROM bank_accounts WHERE bank_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(bankId));
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<BankAccount> getAll() {
		try {
			String sql = "SELECT * FROM bank_accounts";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<BankAccount> output = new ArrayList<BankAccount>();
			while (rs.next()) {
				BankAccount newBankAccount = new BankAccount();
				newBankAccount.setBankAccountId(rs.getInt("bank_id"));
				newBankAccount.setBalance(rs.getDouble("bank_balance"));
				newBankAccount.setUserId(rs.getInt("user_id"));
				output.add(newBankAccount);
			}
			return output;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
