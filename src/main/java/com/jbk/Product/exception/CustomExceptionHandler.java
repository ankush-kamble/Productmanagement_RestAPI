package com.jbk.Product.exception;

import java.util.Date;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public HashMap<String,Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex) {
		
		HashMap<String,Object> map= new HashMap<>();
		map.put("Time",new Date());
		ex.getBindingResult().getFieldErrors().forEach(error ->
		{
			map.put(error.getField(),error.getDefaultMessage());
		});
		return map;
		
	}
	
}
