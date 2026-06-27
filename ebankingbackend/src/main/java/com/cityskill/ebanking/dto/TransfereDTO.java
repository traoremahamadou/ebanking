package com.cityskill.ebanking.dto;

import java.util.Date;

public class TransfereDTO extends AccountOperationDTO {
	
	private String accountSourceid;
	private String accountDestinationid;
	private double amount;
	private String description;
	private String bankAccountId;
	private Date operationDate;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
	
	

}
