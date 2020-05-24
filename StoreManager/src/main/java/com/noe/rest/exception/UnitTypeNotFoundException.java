package com.noe.rest.exception;

public class UnitTypeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UnitTypeNotFoundException(Long id){
		super("Could not find Unit Type " + id);
	}
}
