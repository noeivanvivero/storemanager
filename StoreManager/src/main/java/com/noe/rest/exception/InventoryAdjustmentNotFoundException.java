package com.noe.rest.exception;

public class InventoryAdjustmentNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InventoryAdjustmentNotFoundException(Long id){
		super("Could not find Inventory Adjustment " + id);
	}
}
