package com.cityskill.ebanking.entite;

import java.util.Date;
import java.util.List;

import com.cityskill.ebanking.enumer.AccountStatus;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Stratégie par défaut présentée
@DiscriminatorColumn(name = "TYPE", length = 4)
public class BankAccount {
	  @Id
	    private String id; // Utilisation d'un UUID String [7, 8]
	    private double balance;
	    private Date createdAt;
	    @Enumerated(EnumType.STRING)
	    private AccountStatus status;
	    @ManyToOne
	    private Customer customer;
	    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
	    private List<AccountOperation> accountOperations;
		public BankAccount(String id, double balance, Date createdAt, AccountStatus status, Customer customer,
				List<AccountOperation> accountOperations) {
			super();
			this.id = id;
			this.balance = balance;
			this.createdAt = createdAt;
			this.status = status;
			this.customer = customer;
			this.accountOperations = accountOperations;
		}
		public BankAccount() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public AccountStatus getStatus() {
			return status;
		}
		public void setStatus(AccountStatus status) {
			this.status = status;
		}
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		public List<AccountOperation> getAccountOperations() {
			return accountOperations;
		}
		public void setAccountOperations(List<AccountOperation> accountOperations) {
			this.accountOperations = accountOperations;
		}

}
