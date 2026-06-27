package com.cityskill.ebanking.dto;

public abstract class BankAccountDTO {
	private String type; // Ajouté pour faciliter l'identification côté Frontend [12, 13]
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
