package br.com.zup.proposta.dto;

public class ErrorResponseDto {

	private String mensagem;
	
	public ErrorResponseDto(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}
}
