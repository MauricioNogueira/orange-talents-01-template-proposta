package br.com.zup.proposta.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto {

	private String mensagem;
	private HttpStatus status;
	
	public ResponseDto(String mensagem, HttpStatus status) {
		this.mensagem = mensagem;
		this.status = status;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}

	public HttpStatus getStatus() {
		return status;
	}
}