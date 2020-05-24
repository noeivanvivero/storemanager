package com.noe.rest.exception;

public class SalesDetailNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SalesDetailNotFoundException(Long id){
		super("Could not find Sales Detail " + id);
	}
}
