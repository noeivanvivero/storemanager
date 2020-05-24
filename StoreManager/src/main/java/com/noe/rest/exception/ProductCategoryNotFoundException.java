package com.noe.rest.exception;

public class ProductCategoryNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductCategoryNotFoundException(Long id){
		super("Could not find Product Category " + id);
	}
}
