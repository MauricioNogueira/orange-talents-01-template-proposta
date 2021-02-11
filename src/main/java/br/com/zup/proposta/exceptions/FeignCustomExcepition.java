package br.com.zup.proposta.exceptions;

import org.springframework.http.HttpStatus;

public class FeignCustomExcepition extends RuntimeException {
	
	private final String message;
	private final HttpStatus httpStatus;
	

	public FeignCustomExcepition(String message, HttpStatus httpStatus) {
		super(message);
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}


	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
