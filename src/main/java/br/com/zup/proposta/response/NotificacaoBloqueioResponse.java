package br.com.zup.proposta.response;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NotificacaoBloqueioResponse {

	private String resultado;
	
	@JsonCreator
	public NotificacaoBloqueioResponse(String resultado) {
		this.resultado = resultado;
	}

	public String getResultado() {
		return resultado;
	}
}
