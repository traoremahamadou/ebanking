package com.cityskill.ebanking.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("TRANS")
public class Transfere extends AccountOperation {
	
	private String accountSourceid;
	private String accountDestinationid;
	
	public Transfere() {
		super();
	}
	public Transfere(String accountSourceid, String accountDestinationid) {
		super();
		this.accountSourceid = accountSourceid;
		this.accountDestinationid = accountDestinationid;
	}
	public String getAccountSourceid() {
		return accountSourceid;
	}
	public void setAccountSourceid(String accountSourceid) {
		this.accountSourceid = accountSourceid;
	}
	public String getAccountDestinationid() {
		return accountDestinationid;
	}
	public void setAccountDestinationid(String accountDestinationid) {
		this.accountDestinationid = accountDestinationid;
	}

	
}
