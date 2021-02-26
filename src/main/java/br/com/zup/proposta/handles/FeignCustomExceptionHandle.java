package br.com.zup.proposta.handles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.zup.proposta.dto.ResponseDto;
import br.com.zup.proposta.exceptions.FeignCustomExcepition;

@RestControllerAdvice
public class FeignCustomExceptionHandle {

	@ExceptionHandler(FeignCustomExcepition.class)
	public ResponseEntity<ResponseDto> handle(FeignCustomExcepition e) {
		
		return ResponseEntity.status(e.getHttpStatus()).body(new ResponseDto(e.getMessage(), e.getHttpStatus()));
	}
}
