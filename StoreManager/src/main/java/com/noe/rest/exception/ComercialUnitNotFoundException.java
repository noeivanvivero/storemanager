package com.noe.rest.exception;

public class ComercialUnitNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ComercialUnitNotFoundException(Long id){
		super("Could not find Comercial Unit " + id);
	}
}
