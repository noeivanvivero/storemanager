package com.noe.rest.exception;

public class CustomerNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(Long id){
		super("Could not find Customer " + id);
	}
}
