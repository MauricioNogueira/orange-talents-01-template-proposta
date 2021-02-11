package br.com.zup.proposta.handles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.zup.proposta.dto.FieldErrorDto;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandle {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FieldErrorDto> handle(MethodArgumentNotValidException ex) {
		List<FieldError> fieldsErrors = ex.getFieldErrors();
		List<FieldErrorDto> lista = new ArrayList<>();
		
		fieldsErrors.forEach(fieldError -> {
			lista.add(new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()));
		});
		
		return lista;
	}
}
