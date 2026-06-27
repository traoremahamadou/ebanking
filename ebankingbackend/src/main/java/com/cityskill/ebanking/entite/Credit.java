package com.cityskill.ebanking.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("CREDIT")
public class Credit extends AccountOperation {
	
	private String source;
	
	public Credit() {
		super();
	}
	
	public Credit(String source) {
		super();
		this.source = source;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	

}
