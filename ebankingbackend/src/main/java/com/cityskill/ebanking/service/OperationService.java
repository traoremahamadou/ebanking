package com.cityskill.ebanking.service;

import java.util.List;

import com.cityskill.ebanking.dto.AccountHistoryDTO;
import com.cityskill.ebanking.dto.AccountOperationDTO;
import com.cityskill.ebanking.exception.BalanceNotSufficientException;
import com.cityskill.ebanking.exception.BankAccountNotFoundException;

public interface OperationService {
	 // Opérations
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
//     Historique
  List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException;
  AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
