package br.com.zup.proposta.utils;

import java.math.BigDecimal;

import br.com.zup.proposta.models.Proposta;

public class CriarEntidadeProposta {

	private String nome;
	private String documento;
	private String email;
	private String endereco;
	private BigDecimal salario;
	
	public CriarEntidadeProposta nome(String nome) {
		this.nome = nome;
		
		return this;
	}

	public CriarEntidadeProposta documento(String documento) {
		this.documento = documento;
		return this;
	}

	public CriarEntidadeProposta email(String email) {
		this.email = email;
		return this;
	}

	public CriarEntidadeProposta endereco(String endereco) {
		this.endereco = endereco;
		return this;
	}

	public CriarEntidadeProposta salario(BigDecimal salario) {
		this.salario = salario;
		
		return this;
	}

	public Proposta build() {
		return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salario);
	}
}
