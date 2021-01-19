package dev.thatcher.services;

import java.util.List;

import dev.thatcher.entity.BankAccount;

public interface BankAccountService {
	public BankAccount openNewBankAccount(int userId);
	public List<BankAccount> viewBankAccounts(int userId);
	public double viewBalance(int bankId);
	public BankAccount updateBalance(BankAccount b, double amount);
	public BankAccount deposit(BankAccount b,double amount);
	public BankAccount withdraw(BankAccount b,double amount);
	public boolean deleteAccount(int bankId);
}
