package com.noe.rest.exception;

public class TaxNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public TaxNotFoundException(Long id){
		super("Could not find Tax " + id);
	}
}
