package br.com.zup.proposta.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zup.proposta.models.Biometria;
import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.validations.IsBase64;

public class CadastroBiometriaRequest {

	@NotBlank
	@IsBase64
	private String fingerprint;
	
	@JsonCreator
	public CadastroBiometriaRequest(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public Biometria toModel(Cartao cartao) {
		return new Biometria(cartao, this.fingerprint);
	}
}
