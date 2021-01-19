package dev.thatcher.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.thatcher.app.JDBCconnection;
import dev.thatcher.entity.Transaction;

public class TransactionCrudImpl implements TransactionCrud{

	public static Connection conn = JDBCconnection.getConnection();
	
	public Transaction create(Transaction t) {
		try {
			String sql = "CALL add_transaction(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(t.getBankId()));
			ps.setString(1, Double.toString(t.getAmount()));
			ps.executeQuery();
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t.setTransactionId(rs.getInt("transaction_id"));
				return t;
			}

		} catch (SQLException e) {
			System.out.println("Action Failed");
			e.printStackTrace();
		}
		return t;
	}

	public Transaction create(double amount, int bankId) {
		try {
			String sql = "CALL add_transaction(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(bankId));
			ps.setString(2, Double.toString(amount));
			ps.executeQuery();
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Transaction t = new Transaction();
				t.setBankId(rs.getInt("bank_id"));
				t.setAmount(rs.getDouble("transaction_amount"));
				t.setTransactionId(rs.getInt("transaction_id"));
				return t;
			}

		} catch (SQLException e) {

		}
		return null;
	}

	public Transaction getById(int transactionId) {
		try {
			String sql = "SELECT * FROM transaction WHERE transaction_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(transactionId));
			ResultSet rs = ps.executeQuery();

			Transaction output = new Transaction();
			if (rs.next()) {
				output.setAmount(rs.getDouble("transaction_amount"));
				output.setBankId(rs.getInt("bank_id"));
				output.setTransactionId(rs.getInt("transaction_id"));
				return output;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Transaction update(Transaction t) {
		// Never should be updating any part of our transactions once created
		try {
			String sql = "UPDATE transaction SET transaction_amount = ? WHERE transaction_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Double.toString(t.getAmount()));
			ps.setString(2,  Integer.toString(t.getTransactionId()));
			ps.executeQuery();
			return t;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public boolean delete(int transactionID) {
		try {
			String sql = "DELETE FROM transaction WHERE transaction_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(transactionID));
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Transaction> getAll() {
		try {
			String sql = "SELECT * FROM transaction";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Transaction> output = new ArrayList<Transaction>();
			while (rs.next()) {
				Transaction newTransaction = new Transaction();
				newTransaction.setAmount(rs.getDouble("transaction_amount"));
				newTransaction.setBankId(rs.getInt("bank_id"));
				newTransaction.setTransactionId(rs.getInt("transaction_id"));
				newTransaction.setDate(rs.getString("transaction_date"));
				output.add(newTransaction);
			}
			return output;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
}
