package com.cityskill.ebanking.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cityskill.ebanking.dto.AccountHistoryDTO;
import com.cityskill.ebanking.dto.AccountOperationDTO;
import com.cityskill.ebanking.entite.AccountOperation;
import com.cityskill.ebanking.entite.BankAccount;
import com.cityskill.ebanking.entite.Credit;
import com.cityskill.ebanking.entite.Debit;
import com.cityskill.ebanking.entite.Transfere;
import com.cityskill.ebanking.exception.BalanceNotSufficientException;
import com.cityskill.ebanking.exception.BankAccountNotFoundException;
import com.cityskill.ebanking.mappers.AccoudOperationMapper;
import com.cityskill.ebanking.repository.AccountOperationRepository;
import com.cityskill.ebanking.repository.BankAccountRepository;

@Service
@Transactional

public class AccoudOperationServiceImp implements OperationService {
	
	private static final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private AccoudOperationMapper dtoMapper; // Mapper pour transformer les entités en DTOs [2]
    
    
    public AccoudOperationServiceImp( BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository, AccoudOperationMapper dtoMapper) {
		this.bankAccountRepository = bankAccountRepository;
		this.accountOperationRepository = accountOperationRepository;
		this.dtoMapper = dtoMapper;
	}
    

	@Override
	 public void debit(String bankAccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
		log.info("Enregistrement d'un debit");
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
        
        // Vérification de la règle métier du solde suffisant [17]
        if (bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        
        Debit debit = new Debit();
        debit.setAmount(amount);
        debit.setDescription(description);
        debit.setOperationDate(new Date());
        debit.setBankAccount(bankAccount);
        accountOperationRepository.save(debit);
    
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    } 

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		log.info("Enregistrement d'un credit");  
		BankAccount bankAccount = bankAccountRepository.findById(accountId)
	                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
	        
	        Credit credit = new Credit();	     
	        credit.setAmount(amount);
	        credit.setDescription(description);
	        credit.setOperationDate(new Date());
	        credit.setBankAccount(bankAccount);
	        accountOperationRepository.save(credit);
	        
	        bankAccount.setBalance(bankAccount.getBalance() + amount);
	        bankAccountRepository.save(bankAccount);
	}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
		log.info("Enregistrement d'un transfer");
		// Le virement réutilise les méthodes debit et credit de manière transactionnelle [22, 23]
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
	}
	
	
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
//
//
//	
	@Override
	public List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        List<AccountOperation> ops = bankAccount.getAccountOperations();
        if (ops == null) return java.util.List.of();        
        List<AccountOperationDTO> dtos =
        		ops.stream()
        		.map(acc -> {
        			if(acc instanceof Credit ) {
        				return  dtoMapper.fromCredit((Credit) acc);
        			}else if(acc instanceof Debit) {
        				return dtoMapper.fromDebit((Debit) acc);
        				}else {
        					return  dtoMapper.fromTransfere((Transfere) acc);
        				}		
        		}).collect(Collectors.toList());
//        		List<AccountOperationDTO> dtos =ops;
            return dtos;
            
        		
	}


	
//	public AccountHistoryDTO getAccountHistory(String accountId, int page, int size)
//			throws BankAccountNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	 --- HISTORIQUE ET PAGINATION ---
	@Override
  public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
      BankAccount bankAccount = bankAccountRepository.findById(accountId)
              .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
      
      // Récupération des opérations avec pagination via Spring Data [25]
      Page<AccountOperation> accountOperations = accountOperationRepository
              .findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
      
      AccountHistoryDTO historyDTO = new AccountHistoryDTO();
      List<AccountOperationDTO> operationDTOs = accountOperations.getContent().stream()
    		  .map(acc -> {
      			if(acc instanceof Credit ) {
      				return  dtoMapper.fromCredit((Credit) acc);
      			}else if(acc instanceof Debit) {
      				return dtoMapper.fromDebit((Debit) acc);
      				}else {
      					return  dtoMapper.fromTransfere((Transfere) acc);
      				}		
      		}).collect(Collectors.toList());
      
      historyDTO.setAccountOperationDTOs(operationDTOs);
      historyDTO.setAccountId(bankAccount.getId());
      historyDTO.setBalance(bankAccount.getBalance());
      historyDTO.setCurrentPage(page);
      historyDTO.setPageSize(size);
      historyDTO.setTotalPages(accountOperations.getTotalPages());
      return historyDTO;
  }
} 



