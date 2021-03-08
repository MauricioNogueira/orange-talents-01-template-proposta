package br.com.zup.proposta.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.enums.TipoCarteira;

@Entity
public class Carteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	private String email;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cartao cartao;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoCarteira tipo;
	
	@Deprecated
	public Carteira() {}
	
	public Carteira(String email, Cartao cartao, TipoCarteira tipo) {
		this.email = email;
		this.cartao = cartao;
		this.tipo = tipo;
	}
	
	public Long getId() {
		return id;
	}

	public TipoCarteira getTipo() {
		return tipo;
	}
}
