package dev.thatcher.entity;

public class Transaction {
	private int transactionId = 0;
	private String date = "";
	private double amount;
	private int bankId = 0;

	public Transaction(double amount, int bankId) {
		super();
		this.amount = amount;
		this.setBankId(bankId);
	}

	public Transaction() {
		super();
	}
	
	//GETTERS AND SETTERS
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	//END GETTERS AND SETTERS

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", date=" + date + ", amount=" + amount + ", bankId="
				+ bankId + "]";
	}


}
