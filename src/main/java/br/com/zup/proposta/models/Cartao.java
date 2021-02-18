package br.com.zup.proposta.models;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Cartao {

	@Id
	private String id;
	private LocalDateTime emitidoEm;
	private String titular;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Proposta proposta;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Vencimento vencimento;
	
	@Deprecated
	public Cartao() {}
	
	public Cartao(String id, LocalDateTime emitidoEm, String titular, Proposta proposta, Vencimento vencimento) {
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.proposta = proposta;
		this.vencimento = vencimento;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public Proposta getProposta() {
		return proposta;
	}

	@Override
	public String toString() {
		return "Cartao [id=" + id + ", emitidoEm=" + emitidoEm + ", titular=" + titular + "]";
	}
}
