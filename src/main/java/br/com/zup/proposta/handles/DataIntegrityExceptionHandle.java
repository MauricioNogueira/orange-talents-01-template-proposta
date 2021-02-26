package br.com.zup.proposta.handles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.zup.proposta.dto.ResponseDto;
import br.com.zup.proposta.exceptions.DataIntegrityException;

@RestControllerAdvice
public class DataIntegrityExceptionHandle {

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<ResponseDto> handle(DataIntegrityException ex) {
		
		return ResponseEntity.status(ex.getHttpStatus()).body(new ResponseDto(ex.getMessage(), ex.getHttpStatus()));
	}
}
