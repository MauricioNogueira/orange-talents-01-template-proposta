package br.com.zup.proposta.dto;

import java.time.LocalDateTime;

import br.com.zup.proposta.models.Biometria;

public class BiometriaDto {

	private Long id;
	private String fingerprint;
	private LocalDateTime dataCriacao;
	
	public BiometriaDto(Biometria biometria) {
		this.id = biometria.getId();
		this.fingerprint = biometria.getFingerprint();
		this.dataCriacao = biometria.getDataCriacao();
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public Long getId() {
		return id;
	}
}
