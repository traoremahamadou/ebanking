package com.cityskill.ebanking.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cityskill.ebanking.dto.CurrentBankAccountDTO;
import com.cityskill.ebanking.dto.CustomerDTO;
import com.cityskill.ebanking.dto.SavingBankAccountDTO;
import com.cityskill.ebanking.entite.CurrentAccount;
import com.cityskill.ebanking.entite.Customer;
import com.cityskill.ebanking.entite.SavingAccount;

@Service
public class BankAccountMapperImpl {
    
    // Mapping Customer -> CustomerDTO
    public CustomerDTO fromCustomer(Customer customer) {
        if (customer == null) return null;
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    
    // Mapping CustomerDTO -> Customer
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        if (customerDTO == null) return null;
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
    
    // Mapping CurrentAccount -> CurrentBankAccountDTO
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        if (currentAccount == null) return null;
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        return currentBankAccountDTO;
    }
    
    // Mapping SavingAccount -> SavingBankAccountDTO
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount) {
        if (savingAccount == null) return null;
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        return savingBankAccountDTO;
       
    }
    
//     Mapping AccountOperation -> AccountOperationDTO (CORRIGÉ)
//    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
//        if (accountOperation == null) return null;
//        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
//        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
//        
//        // Ajouter l'ID du compte bancaire si nécessaire
//        if (accountOperation.getBankAccount() != null) {
//        	accountOperationDTO.setBankAccountId(accountOperation.getBankAccount().getId());
//        }
//        
//        return accountOperationDTO;
//    }
}