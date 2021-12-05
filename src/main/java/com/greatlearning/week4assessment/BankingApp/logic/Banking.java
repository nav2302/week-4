package com.greatlearning.week4assessment.BankingApp.logic;

public class Banking {

	public void transferMoney(long amountToTransfer, Customer currentCustomer, Customer customerToSendMoney) {
		if (withdrawBalance(currentCustomer, amountToTransfer)) {
			depositBalance(customerToSendMoney, amountToTransfer);
			System.out.println("Transaction Successful. Current account balance for account = "
					+ currentCustomer.getAcctNumber() + " is = " + currentCustomer.getBalance());
		} else {
			System.out.println("Transaction failed. Not enough Balance in account :-(");
		}
	}

	public boolean depositBalance(Customer currentCustomer, long depositAmount) {
		currentCustomer.setBalance(currentCustomer.getBalance() + depositAmount);
		if (depositAmount >= 0) {
			System.out.println("Amount deposited successfully to account = " + currentCustomer.getAcctNumber()
					+ " . Total balance = " + currentCustomer.getBalance());
			System.out.println("*******************");
			System.out.println();
		}
		return true;
	}

	public boolean withdrawBalance(Customer currentCustomer, long withdrawalAmount) {
		long currentBalance = currentCustomer.getBalance();
		String balanceValidation = currentBalance >= withdrawalAmount ? "Yes" : "No";
		if (balanceValidation.equalsIgnoreCase("Yes")) {

			// Calling deposit balance with negative withdrawal amount
			depositBalance(currentCustomer, -withdrawalAmount);

			System.out.println("Amount = " + withdrawalAmount + " withdrawn successfully from account = "
					+ currentCustomer.getAcctNumber());
			System.out.println("*******************");
			System.out.println();
			return true;
		}
		System.out.println("Withdrawal amount = " + withdrawalAmount + " is greater than current account balance = "
				+ currentBalance);
		System.out.println("*******************");
		System.out.println();
		return false;
	}
}
