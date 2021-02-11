package br.com.zup.proposta.handles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.zup.proposta.dto.ErrorResponseDto;
import br.com.zup.proposta.exceptions.DataIntegrityException;

@RestControllerAdvice
public class DataIntegrityExceptionHandle {

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<ErrorResponseDto> handle(DataIntegrityException ex) {
		
		return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponseDto(ex.getMessage()));
	}
}
