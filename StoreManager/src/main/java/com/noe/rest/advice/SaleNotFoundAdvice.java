package com.noe.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.noe.rest.exception.SaleNotFoundException;

@ControllerAdvice
public class SaleNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(SaleNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String adjustmentTypeNotFoundHandler(SaleNotFoundException ex) {
		return ex.getMessage();
	}
}
