package com.cityskill.ebanking.service;

import java.util.List;

import com.cityskill.ebanking.dto.BankAccountDTO;
import com.cityskill.ebanking.dto.CurrentBankAccountDTO;
import com.cityskill.ebanking.dto.CustomerDTO;
import com.cityskill.ebanking.dto.SavingBankAccountDTO;
import com.cityskill.ebanking.exception.BankAccountNotFoundException;
import com.cityskill.ebanking.exception.CustomerNotFoundException;

public interface BankAccountService {
    // Gestion des Clients
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    List<CustomerDTO> listCustomers();

    // Gestion des Comptes
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> listAccounts();

    // Opérations
//    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
//    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
//    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    
    // Historique
//    List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException;
//    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}