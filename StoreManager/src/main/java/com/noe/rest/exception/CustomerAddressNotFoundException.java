package com.noe.rest.exception;

public class CustomerAddressNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CustomerAddressNotFoundException(Long id){
		super("Could not find Customer Address " + id);
	}
}
