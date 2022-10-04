package com.jbk.Product.exception;

import java.util.Date;

public class ErrorResponse {

	public String getMessgage() {
		return messgage;
	}

	public void setMessgage(String messgage) {
		this.messgage = messgage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ErrorResponse(String messgage, Date date) {
		super();
		this.messgage = messgage;
		this.date = date;
	}

	private String messgage;
	private Date date;
	
	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}
}
