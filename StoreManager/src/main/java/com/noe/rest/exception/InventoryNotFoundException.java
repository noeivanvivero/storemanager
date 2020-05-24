package com.noe.rest.exception;

public class InventoryNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InventoryNotFoundException(Long id){
		super("Could not find Inventory " + id);
	}
}
