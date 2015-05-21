package com.yannic.rdv.rest.sso;

public class SsoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SsoException(String message, Throwable cause) {
		super(message, cause);
	}

	public SsoException(String message) {
		super(message);
	}

}
