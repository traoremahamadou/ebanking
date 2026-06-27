package com.cityskill.ebanking.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("DEBIT")
public class Debit extends AccountOperation{

	
	}
