package com.myshoppingapp.exceptions;

public class DBTransactionException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 7722270063712145100L;

	public DBTransactionException(final String message) {
		super(message);
	}

}
