package br.com.zup.proposta.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Vencimento {

	@Id
	private String id;
	
	@NotNull
	private int dia;
	
	@NotNull
	private LocalDateTime dataCriacao;
	
	@OneToOne(mappedBy = "vencimento")
	private Cartao cartao;
	
	@Deprecated
	public Vencimento() {}

	public Vencimento(String id, int dia, LocalDateTime dataCriacao) {
		this.id = id;
		this.dia = dia;
		this.dataCriacao = dataCriacao;
	}

	public String getId() {
		return id;
	}

	public int getDia() {
		return dia;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	@Override
	public String toString() {
		return "Vencimento [id=" + id + ", dia=" + dia + ", dataCriacao=" + dataCriacao + "]";
	}
}
