package br.com.zup.proposta.models;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.zup.proposta.enums.StatusSolicitacao;
import br.com.zup.proposta.validations.CPFOuCNPJ;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@CPFOuCNPJ
	@Column(unique = true)
	private String documento;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String endereco;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal salario;
	
	private String status;
	
	@OneToOne(mappedBy = "proposta")
	private Cartao cartao;
	
	@Deprecated
	public Proposta() {}
	
	public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Long getId() {
		return id;
	}

	public String getDocumento() {
		return documento;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
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
	
	public void setStatus(StatusSolicitacao statusSolicitacao) {
		this.status = statusSolicitacao.getValue();
	}

	public Cartao getCartao() {
		return cartao;
	}

	@Override
	public String toString() {
		return "Proposta [id=" + id + ", documento=" + documento + ", email=" + email + ", nome=" + nome + ", endereco="
				+ endereco + ", salario=" + salario + ", status=" + status + "]";
	}

	public void vincularCartao(Cartao cartao) {
		this.cartao = cartao;
	}
}
