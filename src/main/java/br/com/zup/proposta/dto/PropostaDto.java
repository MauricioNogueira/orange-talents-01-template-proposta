package br.com.zup.proposta.dto;

import java.math.BigDecimal;

import br.com.zup.proposta.models.Proposta;

public class PropostaDto {

	private String nome;
	private String email;
	private String documento;
	private String endereco;
	private BigDecimal salario;
	private String status;
	
	public PropostaDto(Proposta proposta) {
		this.nome = proposta.getNome();
		this.email = proposta.getEmail();
		this.documento = proposta.getDocumento();
		this.endereco = proposta.getEndereco();
		this.salario = proposta.getSalario();
		this.status = proposta.getStatus();
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getDocumento() {
		return documento;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}
	
	public String getStatus() {
		return this.status;
	}
}
