package com.cityskill.ebanking.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cityskill.ebanking.dto.BankAccountDTO;
import com.cityskill.ebanking.dto.CurrentBankAccountDTO;
import com.cityskill.ebanking.dto.CustomerDTO;
import com.cityskill.ebanking.dto.SavingBankAccountDTO;
import com.cityskill.ebanking.entite.BankAccount;
import com.cityskill.ebanking.entite.CurrentAccount;
import com.cityskill.ebanking.entite.Customer;
import com.cityskill.ebanking.entite.SavingAccount;
import com.cityskill.ebanking.exception.BankAccountNotFoundException;
import com.cityskill.ebanking.exception.CustomerNotFoundException;
import com.cityskill.ebanking.mappers.BankAccountMapperImpl;
import com.cityskill.ebanking.repository.BankAccountRepository;
import com.cityskill.ebanking.repository.CustomerRepository;

@Service
@Transactional
 // Utilise SLF4J via Lombok pour la journalisation [1]
public class BankAccountServiceImpl implements BankAccountService {
    private static final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
//    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper; // Mapper pour transformer les entités en DTOs [2]
    
    
    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, 
//    		AccountOperationRepository accountOperationRepository,
    		BankAccountMapperImpl dtoMapper) {
		this.customerRepository = customerRepository;
		this.bankAccountRepository = bankAccountRepository;
//		this.accountOperationRepository = accountOperationRepository;
		this.dtoMapper = dtoMapper;
	}
    

    // --- GESTION DES CLIENTS ---

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
    	
        log.info("Saving new Customer");

        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        // Utilisation de l'API Stream pour mapper la liste [5, 6]
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customerDTOS;
    } 

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    } 

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Updating Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    } 

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    } 

    // --- GESTION DES COMPTES ---

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) throw new CustomerNotFoundException("Customer not found");
        
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString()); // Génération d'un ID unique [10]
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) throw new CustomerNotFoundException("Customer not found");
        
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    } 

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
        
        // Gestion du polymorphisme via instanceof pour retourner le bon DTO [15, 16]
        if (bankAccount instanceof SavingAccount) {
            return dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
        } else {
            return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
        }
    }

    // --- OPÉRATIONS ---

//    @Override
//    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
//        BankAccount bankAccount = bankAccountRepository.findById(accountId)
//                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
//        
//        // Vérification de la règle métier du solde suffisant [17]
//        if (bankAccount.getBalance() < amount)
//            throw new BalanceNotSufficientException("Balance not sufficient");
//        
//        AccountOperation accountOperation = new AccountOperation();
//        accountOperation.setType(OperationType.DEBIT);
//        accountOperation.setAmount(amount);
//        accountOperation.setDescription(description);
//        accountOperation.setOperationDate(new Date());
//        accountOperation.setBankAccount(bankAccount);
//        accountOperationRepository.save(accountOperation);
//        
//        bankAccount.setBalance(bankAccount.getBalance() - amount);
//        bankAccountRepository.save(bankAccount);
//    } 
//
//    @Override
//    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
//        BankAccount bankAccount = bankAccountRepository.findById(accountId)
//                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
//        
//        AccountOperation accountOperation = new AccountOperation();
//        accountOperation.setType(OperationType.CREDIT);
//        accountOperation.setAmount(amount);
//        accountOperation.setDescription(description);
//        accountOperation.setOperationDate(new Date());
//        accountOperation.setBankAccount(bankAccount);
//        accountOperationRepository.save(accountOperation);
//        
//        bankAccount.setBalance(bankAccount.getBalance() + amount);
//        bankAccountRepository.save(bankAccount);
//    } 
//
//    @Override
//    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
//        // Le virement réutilise les méthodes debit et credit de manière transactionnelle [22, 23]
//        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
//        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
//    } 

    // --- HISTORIQUE ET PAGINATION ---
//    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
//        BankAccount bankAccount = bankAccountRepository.findById(accountId)
//                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
//        
//        // Récupération des opérations avec pagination via Spring Data [25]
//        Page<AccountOperation> accountOperations = accountOperationRepository
//                .findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
//        
//        AccountHistoryDTO historyDTO = new AccountHistoryDTO();
//        List<AccountOperationDTO> operationDTOs = accountOperations.getContent().stream()
//                .map(op -> dtoMapper.fromAccountOperation(op))
//                .collect(Collectors.toList());
//        
//        historyDTO.setAccountOperationDTOs(operationDTOs);
//        historyDTO.setAccountId(bankAccount.getId());
//        historyDTO.setBalance(bankAccount.getBalance());
//        historyDTO.setCurrentPage(page);
//        historyDTO.setPageSize(size);
//        historyDTO.setTotalPages(accountOperations.getTotalPages());
//        return historyDTO;
//    }


	@Override
	public List<BankAccountDTO> listAccounts() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        List<BankAccountDTO> dtos = 
        		accounts.stream().map(acc -> {
            if (acc instanceof SavingAccount) {
                return dtoMapper.fromSavingBankAccount((SavingAccount) acc);
            } else {
                return dtoMapper.fromCurrentBankAccount((CurrentAccount) acc);
            }
        }).collect(Collectors.toList());
        return dtos;
		
		
	}


//	public List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException {
//        BankAccount bankAccount = bankAccountRepository.findById(accountId)
//                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
//        List<AccountOperation> ops = bankAccount.getAccountOperations();
//        if (ops == null) return java.util.List.of();
//        return ops.stream().
//        		map(op -> dtoMapper.fromAccountOperation(op)).
//        		collect(Collectors.toList());
//	} 
}