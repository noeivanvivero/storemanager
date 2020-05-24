package com.noe.rest.exception;

public class UnitOfMeasureNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UnitOfMeasureNotFoundException(Long id){
		super("Could not find Unit Of Measure " + id);
	}
}
