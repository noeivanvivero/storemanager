package com.noe.rest.exception;

public class EmployeeCategoryNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EmployeeCategoryNotFoundException(Long id){
		super("Could not find EmployeeCategory " + id);
	}
}
