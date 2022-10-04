package com.jbk.Product.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> resourceNotFound(ProductNotFoundException exception) {
		
		ErrorResponse response=new ErrorResponse(exception.getMessage(),new Date());
		
		return new ResponseEntity<Object>(response,HttpStatus.OK);
	}
}
