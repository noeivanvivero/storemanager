package com.noe.rest.exception;

public class SucursalNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SucursalNotFoundException(Long id){
		super("Could not find Sucursal " + id);
	}
}
