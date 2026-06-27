package com.cityskill.ebanking.exception;

public class BankAccountNotFoundException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6134962570666426880L;

	public BankAccountNotFoundException(String message) {
        // Appelle le constructeur de la classe parente Exception avec le message d'erreur [4]
        super(message);
    }
}
