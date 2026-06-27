package com.cityskill.ebanking.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cityskill.ebanking.dto.AccountHistoryDTO;
import com.cityskill.ebanking.dto.AccountOperationDTO;
import com.cityskill.ebanking.dto.BankAccountDTO;
import com.cityskill.ebanking.dto.CurrentBankAccountDTO;
import com.cityskill.ebanking.dto.CustomerDTO;
import com.cityskill.ebanking.dto.SavingBankAccountDTO;
import com.cityskill.ebanking.exception.BalanceNotSufficientException;
import com.cityskill.ebanking.exception.BankAccountNotFoundException;
import com.cityskill.ebanking.exception.CustomerNotFoundException;
import com.cityskill.ebanking.service.BankAccountService;
import com.cityskill.ebanking.service.OperationService;

@RestController
@CrossOrigin("*")
public class BankAccountRestController {

    private BankAccountService bankAccountService;
    private OperationService operationService;

    public BankAccountRestController(BankAccountService bankAccountService,
    		OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService=operationService;
    }

    // ==========================
    // GESTION DES CUSTOMERS
    // ==========================

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId)
            throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO) {

        customerDTO.setId(id);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }

    // ==========================
    // GESTION DES COMPTES
    // ==========================

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId)
            throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.listAccounts();
    }

    // Créer compte courant
    @PostMapping("/accounts/current")
    public CurrentBankAccountDTO saveCurrentAccount(
            @RequestParam double initialBalance,
            @RequestParam double overDraft,
            @RequestParam Long customerId)
            throws CustomerNotFoundException {

        return bankAccountService.saveCurrentBankAccount(
                initialBalance,
                overDraft,
                customerId
        );
    }

    // Créer compte épargne
    @PostMapping("/accounts/saving")
    public SavingBankAccountDTO saveSavingAccount(
            @RequestParam double initialBalance,
            @RequestParam double interestRate,
            @RequestParam Long customerId)
            throws CustomerNotFoundException {

        return bankAccountService.saveSavingBankAccount(
                initialBalance,
                interestRate,
                customerId
        );
    }

    // ==========================
    // OPÉRATIONS
    // ==========================

    @PostMapping("/accounts/debit")
    public void debit(
            @RequestParam String accountId,
            @RequestParam double amount,
            @RequestParam String description)
            throws BankAccountNotFoundException,
                   BalanceNotSufficientException {

    	operationService.debit(accountId, amount, description);
    }

    @PostMapping("/accounts/credit")
    public void credit(
            @RequestParam String accountId,
            @RequestParam double amount,
            @RequestParam String description)
            throws BankAccountNotFoundException {

    	operationService.credit(accountId, amount, description);
    }

    @PostMapping("/accounts/transfer")
    public void transfer(
            @RequestParam String accountIdSource,
            @RequestParam String accountIdDestination,
            @RequestParam double amount)
            throws BankAccountNotFoundException,
                   BalanceNotSufficientException {

    	operationService.transfer(
                accountIdSource,
                accountIdDestination,
                amount
        );
    }

    // ==========================
//    // HISTORIQUE
//    // ==========================

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(
            @PathVariable String accountId) throws BankAccountNotFoundException {

        return operationService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size)
            throws BankAccountNotFoundException {

        return operationService.getAccountHistory(
                accountId,
                page,
                size
        );
    }
}