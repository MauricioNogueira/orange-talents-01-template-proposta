package br.com.zup.proposta.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private Cartao cartao;
	
	@NotBlank
	@Lob
	private String fingerprint;
	
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@Deprecated
	public Biometria() {}
	
	public Biometria(Cartao cartao, String fingerprint) {
		this.cartao = cartao;
		this.fingerprint = fingerprint;
	}

	public Long getId() {
		return id;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
}
