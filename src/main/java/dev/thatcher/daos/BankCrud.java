package dev.thatcher.daos;

import dev.thatcher.entity.BankAccount;

public interface BankCrud extends DAO<BankAccount>{
	public BankAccount create(int userId);
	public BankAccount update(BankAccount account, double amount);
	
}
