package br.com.zup.proposta.utils;

import java.math.BigDecimal;

import br.com.zup.proposta.requests.CadastroPropostaRequest;

public class CriaRequestCadastroProposta {
	
	private String nome;
	private String documento;
	private String endereco;
	private BigDecimal salario;
	private String email;

	public CriaRequestCadastroProposta criarProposta() {
		return this;
	}
	
	public CriaRequestCadastroProposta nome(String nome) {
		this.nome = nome;
		
		return this;
	}
	
	public CriaRequestCadastroProposta documento(String documento) {
		this.documento = documento;
		
		return this;
	}
	
	public CriaRequestCadastroProposta endereco(String endereco) {
		this.endereco = endereco;
		return this;
	}
	
	public CriaRequestCadastroProposta email(String email) {
		this.email = email;
		
		return this;
	}
	
	public CriaRequestCadastroProposta salario(BigDecimal salario) {
		this.salario = salario;
		
		return this;
	}
	
	public CadastroPropostaRequest build() {
		return new CadastroPropostaRequest(this.documento, this.email, this.nome, this.endereco, this.salario);
	}
}
