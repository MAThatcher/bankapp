package dev.thatcher.services;

import java.util.List;

import dev.thatcher.entity.Transaction;

public interface TransactionService {
	public List<Transaction> viewTransactions(int bankId);
}
