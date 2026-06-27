package com.cityskill.ebanking.entite;

import java.util.Date;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Stratégie par défaut présentée
@DiscriminatorColumn(name = "TYPE", length = 8)
public class AccountOperation {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
//    @Enumerated(EnumType.STRING)
//    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
	private String description;
//	private Credit credit;
//	private Debit debit;
//	private Transfere transfere;
    
	 
	  public AccountOperation() {
		super();
		// TODO Auto-generated constructor stub
	}
	  public AccountOperation(Long id, Date operationDate, double amount, 
//			  OperationType type, 
			  BankAccount bankAccount, String description) {
		super();
		this.id = id;
		this.operationDate = operationDate;
		this.amount = amount;
//		this.type = type;
		this.bankAccount = bankAccount;
		this.description = description;
	}
	  
	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getOperationDate() {
			return operationDate;
		}
		public void setOperationDate(Date operationDate) {
			this.operationDate = operationDate;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
//		public OperationType getType() {
//			return type;
//		}
//		public void setType(OperationType type) {
//			this.type = type;
//		}
		public BankAccount getBankAccount() {
			return bankAccount;
		}
		public void setBankAccount(BankAccount bankAccount) {
			this.bankAccount = bankAccount;
		}
		public void setDescription(String description) {
			this.description=description;
			
		}
		public String getDescription() {
			return description;
		}
//		public Credit getCredit() {
//			return credit;
//		}
//		public void setCredit(Credit credit) {
//			this.credit = credit;
//		}
//		public Debit getDebit() {
//			return debit;
//		}
//		public void setDebit(Debit debit) {
//			this.debit = debit;
//		}
//		public Transfere getTransfere() {
//			return transfere;
//		}
//		public void setTransfere(Transfere transfere) {
//			this.transfere = transfere;
//		}
//		
}
