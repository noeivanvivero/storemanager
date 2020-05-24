package com.noe.rest.exception;

public class SaleNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SaleNotFoundException(Long id){
		super("Could not find Sale " + id);
	}
}
