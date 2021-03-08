package br.com.zup.proposta.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseDto {

	private String mensagem;
	
	@JsonIgnore
	private HttpStatus status;
	
	@JsonInclude(value = Include.NON_NULL)
	private List<FieldErrorDto> erros;
	
	@JsonInclude(value = Include.NON_NULL)
	private Object data;
	
	public ResponseDto(String mensagem, HttpStatus status) {
		this.mensagem = mensagem;
		this.status = status;
	}
	
	public ResponseDto(String mensagem, HttpStatus status, List<FieldErrorDto> erros) {
		this.mensagem = mensagem;
		this.status = status;
		this.erros = erros;
	}
	
	public ResponseDto(String mensagem, HttpStatus status, Object data) {
		this.mensagem = mensagem;
		this.status = status;
		this.data = data;
	}

	public String getMensagem() {
		return this.mensagem;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public List<FieldErrorDto> getErros() {
		return erros;
	}
	
	public Object getData() {
		return data;
	}
}