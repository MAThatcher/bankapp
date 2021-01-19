package dev.thatcher.app;

import java.util.List;
import java.util.Scanner;

import dev.thatcher.entity.BankAccount;
import dev.thatcher.entity.UserAccount;
import dev.thatcher.services.BankAccountService;
import dev.thatcher.services.BankAccountServiceImpl;
import dev.thatcher.services.TransactionService;
import dev.thatcher.services.TransactionServiceImpl;
import dev.thatcher.services.UserAccountService;
import dev.thatcher.services.UserAccountServiceImpl;

public class App {
	public static Scanner scan = new Scanner(System.in);
	private static UserAccount loggedInUser = null;
	private static UserAccountService user_service = new UserAccountServiceImpl();
	private static BankAccountService bank_service = new BankAccountServiceImpl();
	private static TransactionService transaction_service = new TransactionServiceImpl();

	public static void main(String[] args) {
		while (true) {
			int input = -1;
			String scannerInput = "";
			if (loggedInUser == null) {
				System.out.println("Welcome to the banking application created by Mitchell Thatcher");
				System.out.println("Please enter:\n1 Login\n2 Create new account\n3 Close program");

				String username, password;
				scannerInput = scan.next();
				try {
					input = Integer.parseInt(scannerInput);
				} catch (NumberFormatException e) {
					System.out.println("Incorrect Input");
					// If a bad input is given, restart the loop
					continue;
				}

				switch (input) {
				case 1:
					// Login selected
					System.out.println("Please enter your username");
					username = scan.next();
					System.out.println("Please enter your password");
					password = scan.next();
					loggedInUser = user_service.logIn(username, password);
					break;
				case 2:
					// Register new user selected
					System.out.println("Please enter your desired username");
					username = scan.next();
					System.out.println("Please enter your desired password");
					password = scan.next();
					user_service.createAccount(username, password);
					break;
				case 3:
					// End this program
					scan.close();
					return;
				default:
					System.out.println("Your input of " + input + " is not a valid option");
					break;
				}
			}

			if (loggedInUser != null) {
				loggedInPage(loggedInUser);
			}
		}
	}

	public static void loggedInPage(UserAccount user) {
		while (true) {
			System.out.println("Hello " + user.getUsername());
			System.out.println("What would you like to do?");
			System.out.println(
					"1 View accounts and balances\n2 Create new bank account\n3 Delete my account\n4 Edit account\n5 Logout");
			int input;

			// Do not display this menu option unless it is a super user logged in
			if (user.isSuperUser()) {
				System.out.println("6 View super user options");
			}

			String scannerInput = scan.next();
			try {
				input = Integer.parseInt(scannerInput);
			} catch (NumberFormatException e) {
				System.out.println("Incorrect Input");
				return;
			}

			switch (input) {
			case 1:
				viewAccountsAndBalances(user);
				break;
			case 2:
				bank_service.openNewBankAccount(user.getId());
				break;
			case 3:
				if (warning()) {
					if (user_service.deleteAccount(user)) {//returns a boolean if user_service deletes the account properly
						System.out.println("Account deleted");
						if (loggedInUser == user) {// No more account to be logged into. So log out
							loggedInUser = null;
							input = -1;
						}
						return;
					}
				}
				break;
			case 4:
				editAccountPage(user);
				break;
			case 5:
				// logout // Because a super user can log in and log out while viewing another
				// user
				// Check to see if the user is actually the loggedInUser. If its not then
				// a super user is logged in as someone else so just return;
				if (loggedInUser == user) {
					loggedInUser = null;
					input = -1;
				}
				return;
			case 6:
				if (user.isSuperUser()) {
					superUserMenu(user);
				} else {
					System.out.println("Your input of " + input + " is not a valid option");
				}
				break;
			default:
				System.out.println("Your input of " + input + " is not a valid option");
				break;
			}
		}

	}

	public static void editAccountPage(UserAccount user) {
		while (true) {
			System.out.println(user.getUsername() + ", What would you like to do?");
			System.out.println("1\tChange username\n2\tChange password\n3\tReturn to previous menu");
			if (loggedInUser.isSuperUser()) {
				System.out.println("4\tChange to superuser");
			}
			String scannerInput = scan.next();
			int input = -1;
			try {
				input = Integer.parseInt(scannerInput);
			} catch (NumberFormatException e) {
				System.out.println("Incorrect Input");
				return;
			}

			switch (input) {
			case 1:
				System.out.println("Please enter desired username");
				scannerInput = scan.next();
				user_service.changeUsername(user, scannerInput);
				break;
			case 2:
				System.out.println("Please enter desired password");
				scannerInput = scan.next();
				user_service.changePassword(user, scannerInput);
				break;
			case 3:
				return;
			case 4:
				if (loggedInUser.isSuperUser()) {
					user_service.makeSuperUser(user);
					break;
				}
				System.out.println("Your input of " + input + " is not a valid option");
				break;
			default:
				System.out.println("Your input of " + input + " is not a valid option");
				break;
			}
		}
	}

	public static void viewAccountsAndBalances(UserAccount user) {
		while (true) {
			// Cannot view accounts and balances if there are no accounts and balances
			// if there are no accounts then return this function and send back to the
			// logged in page
			if (bank_service.viewBankAccounts(user.getId()).isEmpty()) {
				System.out.println("You have no open accounts");
				return;
			}

			// get all bank accounts belonging to our user
			List<BankAccount> bankAccounts;
			bankAccounts = bank_service.viewBankAccounts(user.getId());

			// Display all bank accounts to our user along with a number to enter to access
			// the bank accounts
			for (int i = 0; i < bankAccounts.size(); i++) {
				System.out.println("Press " + (i + 1) + " for\tBank account id:"
						+ bankAccounts.get(i).getBankAccountId() + "\tbalance:" + bankAccounts.get(i).getBalance());
			}
			System.out.println("Press " + (bankAccounts.size() + 1) + " to return to the previous menu");

			int input = 0;
			String scannerInput = scan.next();
			try {
				input = Integer.parseInt(scannerInput);
				input--;// The list of accounts user sees should start at 1 not 0
				// decrement input so user input matches with the index of the desired bank
				// account
			} catch (NumberFormatException e) {
				System.out.println("Incorrect Input");
				continue;
			}

			// user wishes to return to previous menu.
			if (input == bankAccounts.size()) {
				return;
			}

			// User entered an input desiring to view a particular bank account
			if (input >= 0 && input < bankAccounts.size()) {
				bankAccountMenu(user, bankAccounts.get(input));
			} else {
				System.out.println("Your input of " + input + "is not a valid option");
			}
		}

	}

	public static void bankAccountMenu(UserAccount user, BankAccount bankAccount) {
		while (true) {
			System.out.println("Bank account selected, ID:" + bankAccount.getBankAccountId() + " Balance: "
					+ bankAccount.getBalance());
			System.out.println(
					"1 Deposit\n2 Withdraw\n3 Delete account\n4 View transaction history \n5 Return to previous menu");
			int input = 0;

			String scannerInput = scan.next();
			try {
				input = Integer.parseInt(scannerInput);
			} catch (NumberFormatException e) {
				System.out.println("Incorrect Input");
				continue;
			}

			switch (input) {

			case 1:
				System.out.println("How much would you like to deposit?");
				double depositAmount = 0;
				scannerInput = scan.next();
				try {
					depositAmount = Double.parseDouble(scannerInput);
				} catch (NumberFormatException e) {
					System.out.println("Incorrect Input");
					continue;
				}
				bankAccount = bank_service.deposit(bankAccount, depositAmount);
				break;

			case 2:
				System.out.println("How much would you like to withdraw?");
				double withdrawAmount = 0;
				scannerInput = scan.next();
				try {
					withdrawAmount = Double.parseDouble(scannerInput);
				} catch (NumberFormatException e) {
					System.out.println("Incorrect Input");
					continue;
				}
				bankAccount = bank_service.withdraw(bankAccount, withdrawAmount);
				break;

			case 3:
				if (bankAccount.getBalance() != 0) {
					System.out.println("Balance must be 0 to close this bank account");
				} else {
					if (warning()) {
						bank_service.deleteAccount(bankAccount.getBankAccountId());
						return;
					}
				}
				break;

			case 4:
				transaction_service.viewTransactions(bankAccount.getBankAccountId());
				break;

			case 5:
				return;

			default:
				System.out.println("Your input of " + input + " is not a valid option");
				break;
			}
		}

	}

	public static void superUserMenu(UserAccount user) {
		while (true) {
			System.out
					.println("Welcome " + user.getUsername() + " to the super user menu. Your options are as follows");
			System.out.println(
					"1 Delete all user accounts\n2 Create a new user account\n3 View users\n4 Return to previous menu");
			int input = 1;
			String scannerInput = scan.next();
			try {
				input = Integer.parseInt(scannerInput);
			} catch (NumberFormatException e) {
				System.out.println("Incorrect Input");
				continue;
			}

			switch (input) {
			case 1:
				System.out.println(
						"This deletes all User accounts and All their bank acounts!!! This will not delete your account");
				if (warning()) {
					user_service.deleteAllUsers(loggedInUser);
					System.out.println("All users deleted");
				}

				break;
			case 2:
				// create a new user
				System.out.println("Please enter your desired username");
				String username = scan.next();
				System.out.println("Please enter your desired password");
				String password = scan.next();
				user_service.createAccount(username, password);
				break;
			case 3:
				viewAllUsers(loggedInUser);
				break;
			case 4:
				return;
			default:
				System.out.println("Your input of " + input + " is not a valid option");
				break;
			}

		}
	}

	public static void viewAllUsers(UserAccount user) {
		while (true) {

			List<UserAccount> users = user_service.viewAllUsers(user);

			for (int i = 0; i < users.size(); i++) {
				System.out.println("Press " + (i + 1) + " for\tUsername:" + users.get(i).getUsername());
			}

			System.out.println("Press " + (users.size() + 1) + " for the previous menu");

			int input = -1;
			String scannerInput = scan.next();
			try {
				input = Integer.parseInt(scannerInput);
				input--;
			} catch (NumberFormatException e) {
				System.out.println("Incorrect Input");
				continue;
			}

			if (input == users.size()) {
				return;
			}

			// Our super user can log into any account and edit that account as though they
			// were in fact that user
			if (input >= 0 && input < users.size()) {
				loggedInPage(users.get(input));
			} else {
				System.out.println("Your input of " + input + " is not a valid option");
			}
		}
	}

	// Returns true if user wishes to continue with action
	public static boolean warning() {
		System.out.println("WARNING!!!!");
		System.out.println("What you are about to do can not be undone");
		System.out.println("1: Continue");
		System.out.println("2: Return");

		int input = -1;
		String scannerInput = scan.next();
		try {
			input = Integer.parseInt(scannerInput);
		} catch (NumberFormatException e) {
			System.out.println("Incorrect Input - Action Canceled ");
			return false;
		}

		if (input == 1) {
			return true;
		}
		System.out.println("Action Canceled");
		return false;
	}
}