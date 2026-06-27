package com.cityskill.ebanking;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cityskill.ebanking.dto.BankAccountDTO;
import com.cityskill.ebanking.dto.CurrentBankAccountDTO;
import com.cityskill.ebanking.dto.CustomerDTO;
import com.cityskill.ebanking.dto.SavingBankAccountDTO;
import com.cityskill.ebanking.entite.Credit;
import com.cityskill.ebanking.entite.CurrentAccount;
import com.cityskill.ebanking.entite.Customer;
import com.cityskill.ebanking.entite.Debit;
import com.cityskill.ebanking.entite.SavingAccount;
import com.cityskill.ebanking.entite.Transfere;
import com.cityskill.ebanking.enumer.AccountStatus;
import com.cityskill.ebanking.exception.BalanceNotSufficientException;
import com.cityskill.ebanking.exception.BankAccountNotFoundException;
import com.cityskill.ebanking.exception.CustomerNotFoundException;
import com.cityskill.ebanking.repository.AccountOperationRepository;
import com.cityskill.ebanking.repository.BankAccountRepository;
import com.cityskill.ebanking.repository.CustomerRepository;
import com.cityskill.ebanking.service.BankAccountService;
import com.cityskill.ebanking.service.OperationService;

@SpringBootApplication
public class EbankingbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingbackendApplication.class, args);
	}
//@Bean
	CommandLineRunner start(CustomerRepository customerRepository, 
	                        BankAccountRepository bankAccountRepository, 
	                        AccountOperationRepository accountOperationRepository
	                        ) {
	    return args -> {
	        Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
	            Customer customer = new Customer();
	            customer.setName(name);
	            customer.setEmail(name + "@gmail.com");
	            customerRepository.save(customer);
	        });

	        customerRepository.findAll().forEach(cust -> {
	            // Création d'un compte courant
	            CurrentAccount currentAccount = new CurrentAccount();
	            currentAccount.setId(UUID.randomUUID().toString());
	            currentAccount.setBalance(Math.random() * 90000);
	            currentAccount.setCreatedAt(new Date());
	            currentAccount.setStatus(AccountStatus.CREATED);
	            currentAccount.setCustomer(cust);
	            currentAccount.setOverDraft(9000);
	            bankAccountRepository.save(currentAccount);

	            // Création d'un compte épargne
	            SavingAccount savingAccount = new SavingAccount();
	            savingAccount.setId(UUID.randomUUID().toString());
	            savingAccount.setBalance(Math.random() * 90000);
	            savingAccount.setCreatedAt(new Date());
	            savingAccount.setStatus(AccountStatus.CREATED);
	            savingAccount.setCustomer(cust);
	            savingAccount.setInterestRate(5.5);
	            bankAccountRepository.save(savingAccount);
	        });

	        // Ajout d'opérations aléatoires
	        bankAccountRepository.findAll().forEach(acc -> {
	            for (int i = 0; i < 10; i++) {
//	                AccountOperation op = new AccountOperation();
//	                op.setOperationDate(new Date());
//	                op.setAmount(Math.random() * 12000);
//	                op.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
//	                op.setBankAccount(acc);
//	                accountOperationRepository.save(op);
	                
	                Debit debit =new Debit();
	                debit.setOperationDate(new Date());
	                debit.setAmount(Math.random() * 12000);	               
	                debit.setBankAccount(acc);
	                accountOperationRepository.save(debit);
	                
	                
	                Credit credit =new Credit();
	                credit.setOperationDate(new Date());
	                credit.setAmount(Math.random() * 12000);	               
	                credit.setBankAccount(acc);
	                accountOperationRepository.save(debit);
	                
	                
	                Transfere transfere =new Transfere();
	                transfere.setOperationDate(new Date());
	                transfere.setAmount(Math.random() * 12000);	               
	                transfere.setBankAccount(acc);
	                accountOperationRepository.save(debit);
	            }
	        });
	    };
	}



@Bean
CommandLineRunner commandLineRunner(BankAccountService bankAccountService,
									OperationService accoudOperationService) {
    return args -> {
        // 1. Création de clients de test
        Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName(name);
            customerDTO.setEmail(name + "@gmail.com");
            bankAccountService.saveCustomer(customerDTO);
        });

        // 2. Pour chaque client, créer un compte courant et un compte épargne
        bankAccountService.listCustomers().forEach(customer -> {
            try {
                bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
            }
        });

        // 3. Pour chaque compte, créer une dizaine d'opérations de débit et crédit
        List<BankAccountDTO> bankAccounts = bankAccountService.listAccounts();
        for (BankAccountDTO bankAccount : bankAccounts) {
            for (int i = 0; i < 10; i++) {
                String accountId;
                // Vérification du type pour récupérer l'ID (car BankAccountDTO est abstrait)
                if (bankAccount instanceof SavingBankAccountDTO) {
                    accountId = ((SavingBankAccountDTO) bankAccount).getId();
                } else {
                    accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                }
                
                try {
                	accoudOperationService.credit(accountId, 10000 + Math.random() * 120000,"credit");
                	accoudOperationService.debit(accountId, 1000 + Math.random() * 9000,"debit");
                } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
}
