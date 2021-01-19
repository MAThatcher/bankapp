package dev.thatcher.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import dev.thatcher.daos.BankCrud;
import dev.thatcher.daos.BankCrudImpl;
import dev.thatcher.entity.BankAccount;

public class BankAccountServiceImpl implements BankAccountService {
	private static BankCrud bankCrud = new BankCrudImpl();

	public BankAccount openNewBankAccount(int userId) {
		System.out.println("Bank Account Created");
		return bankCrud.create(userId);
	}

	public List<BankAccount> viewBankAccounts(int userId) {
		List<BankAccount> b = bankCrud.getAll();
		List<BankAccount> output = new ArrayList<BankAccount>();
		for (BankAccount i : b) {
			if (i.getUserId() == userId) {
				output.add(i);
			}
		}
		return output;
	}

	public double viewBalance(int bankId) {
		BankAccount b = bankCrud.getById(bankId);
		return b.getBalance();
	}

	public BankAccount updateBalance(BankAccount input, double amount) {
		try {
			BankAccount b = bankCrud.getById(input.getBankAccountId());
			// ensure that value being added only extends to 2 decimal places
			Double truncatedAmount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
			Double truncatedBalance = BigDecimal.valueOf(amount + b.getBalance()).setScale(2, RoundingMode.HALF_UP)
					.doubleValue();
			b.setBalance(truncatedBalance);

			// Two different updates are called intentionally. First one updates the bank
			// table
			// second creates a transaction
			bankCrud.update(b);
			bankCrud.update(b, truncatedAmount);
			System.out.println("Balance Updated, New balance: " + b.getBalance());
			return b;
		} catch (Exception e) {
			System.out.println("Something went wrong");
		}
		return input;
	}

	public BankAccount deposit(BankAccount b, double amount) {
		if (amount < 0) {
			System.out.println("Cannot deposit a negitive amount");
			return b;
		} else {
			return this.updateBalance(b, amount);
		}
	}

	public BankAccount withdraw(BankAccount b, double amount) {
		if (amount < 0) {
			System.out.println("Cannot withdraw a negitive amount");
			return b;
		} else {
			return this.updateBalance(b, (-1 * amount));
		}
	}

	public boolean deleteAccount(int bankId) {
		if (bankCrud.getById(bankId).getBalance() != 0) {
			System.out.println("Cannot close an your user account. Balance of all bank accounts must be 0");
			return false;
		} else {
			bankCrud.delete(bankId);
			System.out.println("Bank Account Deleted");
		}
		return true;

	}
}
