package br.com.zup.proposta.exceptions;

import org.springframework.http.HttpStatus;

public class DataIntegrityException extends RuntimeException {
	
	private final String message;
	private final HttpStatus httpStatus;

	public DataIntegrityException(String message, HttpStatus httpStatus) {
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
