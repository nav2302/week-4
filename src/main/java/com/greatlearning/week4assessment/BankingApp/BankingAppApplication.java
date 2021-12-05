package com.greatlearning.week4assessment.BankingApp;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.greatlearning.week4assessment.BankingApp.logic.*;
import java.util.InputMismatchException;


public class BankingAppApplication {

	//Static list of Customer account and Password (Please use any one of the below for testing)
	public static List<Customer> customerDetails = new ArrayList<Customer>() {
		private static final long serialVersionUID = 1L;

		{
			add(new Customer("acct1", "pass1", 0L));
			add(new Customer("acct2", "pass2", 0L));
		}
	};

	public static Path path = Paths.get("src/main/resources/temp.txt");
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Welcome to the login page!!!!!");
		Customer currentCustomer = null;

		while (true) {
			System.out.println("Enter the bank account number");
			String bankAcctNumber = scanner.nextLine();
			System.out.println("Enter the password for the corresponding bank account no.");
			String password = scanner.nextLine();

			currentCustomer = customerDetails.stream()
					.filter(customer -> bankAcctNumber.equals(customer.getAcctNumber())
							&& password.equals(customer.getPassword()))
					.findFirst().orElse(null);
			
			if (currentCustomer != null)
				break;
			System.out.println("Invalid Credentials. Try Again!!");
		}

		System.out.println("!!!!! Welcome to Indian Bank !!!!!");
		System.out.println();

		// Using Java 8 feature of Auto close and flush the writer
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write("***************ACCOUNT = " + currentCustomer.getAcctNumber());
			writer.newLine();
			
			Banking bankingTasks = new Banking();
			
			while (true) {

				System.out.println("Enter the operation you want to perform");
				System.out.println("1. Deposit");
				System.out.println("2. Withdrawal");
				System.out.println("3. Transfer");
				System.out.println("0. Logout");

				boolean logout = false;
				int actionNumberToPerform = 0;
				try {
					actionNumberToPerform = scanner.nextInt();
					scanner.nextLine();
				}
				catch(InputMismatchException e) {
					System.out.println(e.getMessage());
				}

				switch (actionNumberToPerform) {
				case 0:
					logout = true;
					break;
				case 1:
					System.out.println("Enter the amount for deposit");
					long depositAmount = scanner.nextLong();
					scanner.nextLine();
				

					// Write the deposit to the file, whether it was successful or not!
					if (bankingTasks.depositBalance(currentCustomer, depositAmount))
						writer.write("Amount deposited = " + String.valueOf(depositAmount));
					writer.newLine();
					break;
				case 2:
					System.out.println("Enter the amount for withdrawal");
					long withdrawalAmount = scanner.nextLong();
					scanner.nextLine();

					// Write the withdrawal to the file, whether it was successful or not!
					if (bankingTasks.withdrawBalance(currentCustomer, withdrawalAmount))
						writer.write("Amount withdrawn = " + String.valueOf(withdrawalAmount));
					else
						writer.write("Error :: Amount cannot be withdrawn");
					writer.newLine();
					break;
				case 3:
					System.out.println("Enter the registered mobile number ");
					String number = scanner.nextLine();

					SecureRandom random = new SecureRandom();
					int num = random.nextInt(100000);
					String OTP = String.format("%04d", num);

					// Displaying the sent OTP in case the SMS API is down or any other issue
					System.out.println("Sent OPT is = " + OTP);

					// Sending SMS to mobile via FAST2SMS API
					SmsApi.sendSms(OTP, number);

					System.out.println("Enter the OTP received");
					String OTPreceived = scanner.nextLine();

					if (OTPreceived.equalsIgnoreCase(OTP)) {
						System.out.println("OTP verification Successful !!!");

						System.out.println("Enter BankAccount to which you want to transfer money");
						String bankAccountToTransfer = scanner.nextLine();
						System.out.println("Enter the amount to transfer");
						long amountToTransfer = scanner.nextLong();

						Customer customerToSendMoney = customerDetails.stream()
								.filter(customer -> bankAccountToTransfer.equals(customer.getAcctNumber())).findFirst()
								.orElse(null);
						scanner.nextLine();
						if (customerToSendMoney != null) {
							bankingTasks.transferMoney(amountToTransfer, currentCustomer, customerToSendMoney);
							writer.write("Transaction of amount = " + amountToTransfer + " successful to account = "
									+ customerToSendMoney.getAcctNumber());
						} else {
							System.out.println("Account not exists");
							writer.write("Error :: Transaction Unsuccessful");
						}
					} else {
						System.out.println("Wrong OTP entered");
					}
					break;
				}
				
				if (logout)
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		scanner.close();
	}

}
