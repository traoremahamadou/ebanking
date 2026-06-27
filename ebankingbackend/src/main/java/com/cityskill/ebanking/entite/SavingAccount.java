package com.cityskill.ebanking.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SA")
public class SavingAccount extends BankAccount {
	 private double interestRate;

	 public double getInterestRate() {
		return interestRate;
	}

	 public void setInterestRate(double interestRate) {
		 this.interestRate = interestRate;
	 }

	 public SavingAccount(double interestRate) {
		super();
		this.interestRate = interestRate;
	}

	 public SavingAccount() {
		super();
		// TODO Auto-generated constructor stub
	 }

}
