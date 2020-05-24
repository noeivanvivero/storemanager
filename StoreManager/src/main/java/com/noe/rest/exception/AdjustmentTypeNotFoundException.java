package com.noe.rest.exception;

public class AdjustmentTypeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AdjustmentTypeNotFoundException(Long id){
		super("Could not find Adjustment Type " + id);
	}
}
