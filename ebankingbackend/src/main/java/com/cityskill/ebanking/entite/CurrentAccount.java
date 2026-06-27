package com.cityskill.ebanking.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CA")
public class CurrentAccount extends BankAccount {
	private double overDraft;

	public double getOverDraft() {
		return overDraft;
	}

	public void setOverDraft(double overDraft) {
		this.overDraft = overDraft;
	}

	public CurrentAccount(double overDraft) {
		super();
		this.overDraft = overDraft;
	}

	public CurrentAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
