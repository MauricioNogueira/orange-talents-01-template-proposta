package br.com.zup.proposta.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificaBloqueioCartaoRequest {

	@JsonProperty
	private String sistemaResponsavel;
	
	@JsonCreator
	public NotificaBloqueioCartaoRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}
	
	public String getSistemaResponsavel() {
		return this.sistemaResponsavel;
	}
}
