package com.cityskill.ebanking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cityskill.ebanking.entite.AccountOperation;

public interface AccountOperationRepository extends org.springframework.data.jpa.repository.JpaRepository<com.cityskill.ebanking.entite.AccountOperation, Long> {

	Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, PageRequest of);

}
