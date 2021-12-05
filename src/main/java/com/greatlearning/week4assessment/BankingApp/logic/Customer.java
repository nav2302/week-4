package com.greatlearning.week4assessment.BankingApp.logic;

public class Customer {

	private String acctNumber;
	private String password;
	private Long balance;

	public Customer() {
	}

	public Customer(String acctNumber, String password, Long balance) {
		super();
		this.acctNumber = acctNumber;
		this.password = password;
		this.balance = balance;
	}



	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Customer [acctNumber=" + acctNumber + ", password=" + password + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acctNumber == null) ? 0 : acctNumber.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (acctNumber == null) {
			if (other.acctNumber != null)
				return false;
		} else if (!acctNumber.equals(other.acctNumber))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
