package com.cityskill.ebanking.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cityskill.ebanking.dto.CreditDTO;
import com.cityskill.ebanking.dto.DebitDTO;
import com.cityskill.ebanking.dto.TransfereDTO;
import com.cityskill.ebanking.entite.Credit;
import com.cityskill.ebanking.entite.Debit;
import com.cityskill.ebanking.entite.Transfere;

@Service
public class AccoudOperationMapper {
	
	public DebitDTO fromDebit(Debit debit) {
		if (debit == null) return null;
		DebitDTO debitDTO = new DebitDTO();
		BeanUtils.copyProperties(debit, debitDTO);
		 if (debit.getBankAccount()!= null) {
			  debitDTO.setBankAccountId(debit.getBankAccount().getId());
	        }
		return debitDTO;
	}
//		public Debit fromDebitDTO(DebitDTO debitDTO) {
//			if (debitDTO == null) return null;
//			Debit debit = new Debit();
//			BeanUtils.copyProperties(debitDTO, debit);
//			debit.setBankAccountId(debit.getBankAccount().getId());
//			
//			 if (debitDTO.getBankAccount()!= null) {
//				  debitDTO.setBankAccountId(debit.getBankAccount().getId());
//		        }
//			return debit;
//			
//		}
		
		
		public CreditDTO fromCredit(Credit credit) {
			if (credit == null) return null;
			CreditDTO creditDTO = new CreditDTO();
			BeanUtils.copyProperties(credit,creditDTO);
			 if (credit.getBankAccount()!= null) {
				 creditDTO.setBankAccountId(credit.getBankAccount().getId());
		        }
			return creditDTO;
	}
//			public Credit fromCreditDTO(DebitDTO creditDTO) {
//				if (creditDTO==null) return null;
//				Credit credit = new Credit();
//				BeanUtils.copyProperties(creditDTO, credit);
//				 if (debit.getBankAccount()!= null) {
//					  debitDTO.setBankAccountId(debit.getBankAccount().getId());
//			        }
//				return credit;
//			}
			
			
			public TransfereDTO fromTransfere(Transfere transfere) {
				if (transfere == null) return null;
				TransfereDTO transfereDTO = new TransfereDTO();
				BeanUtils.copyProperties(transfere,transfereDTO);
				 if (transfere.getBankAccount()!= null) {
					 transfereDTO.setBankAccountId(transfere.getBankAccount().getId());
			        }
				return transfereDTO;
			}
//				public Transfere fromTransfere(TransfereDTO transfereDTO) {
//					if (transfereDTO==null) return null;
//					Transfere transfere = new Transfere();
//					BeanUtils.copyProperties(transfereDTO, transfere);
//					 if (debit.getBankAccount()!= null) {
//						  debitDTO.setBankAccountId(debit.getBankAccount().getId());
//				        }
//					return transfere;
//				}
//			public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
//				if(accountOperation instanceof Credit ) {
//					return dtoMapper.fromCredit((Credit)accountOperation);
//				} else if(accountOperation instanceof Debit ) {
//					return dtoMapper.fromDebit((Debit)accountOperation);
//				}else if(accountOperation instanceof Transfere ) {
//					return dtoMapper.fromTransfere((Transfere)accountOperation);
//				}throw new IllegalArgumentException("Type d'operation inconnus");	
//			}
		
		

}
