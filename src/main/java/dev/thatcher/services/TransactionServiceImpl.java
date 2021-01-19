package dev.thatcher.services;

import java.util.ArrayList;
import java.util.List;

import dev.thatcher.daos.TransactionCrud;
import dev.thatcher.daos.TransactionCrudImpl;
import dev.thatcher.entity.Transaction;

public class TransactionServiceImpl implements TransactionService{
	
	private static TransactionCrud transCrud = new TransactionCrudImpl();

	public List<Transaction> viewTransactions(int bankId) {
		List<Transaction> output = new ArrayList<Transaction>();
		List<Transaction> t = transCrud.getAll();
		System.out.println("Transaction history for Bank account :" + bankId);
		if (t.size() == 0) {
			System.out.println("No transactions recorded");
		}
		for (Transaction i : t) {
			if (i.getBankId() == bankId) {
				output.add(i);
				System.out.println("Transaction ID:" + i.getTransactionId() +"\n\tAmount:" +i.getAmount());
				System.out.println("\tDate:" + i.getDate());
			}
		}
		return output;
	}

}
