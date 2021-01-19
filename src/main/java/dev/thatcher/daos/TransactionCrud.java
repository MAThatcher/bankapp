package dev.thatcher.daos;

import dev.thatcher.entity.Transaction;

public interface TransactionCrud extends DAO<Transaction>{
	public Transaction create(double amount, int bankId);
	public Transaction update(Transaction t);
}
