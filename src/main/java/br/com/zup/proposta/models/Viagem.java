package br.com.zup.proposta.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Viagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String destino;
	
	@NotNull
	@Future
	private LocalDate dataTermino;
	
	private LocalDateTime dataAviso = LocalDateTime.now();
	
	@NotBlank
	private String ip;
	
	@NotBlank
	private String userAgent;
	
	@NotBlank
	private String identificadorCartao;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cartao cartao;
	
	@Deprecated
	public Viagem() {}
	
	public Viagem(String destino, LocalDate dataTermino, String ip, String userAgent, String identificadorCartao) {
		this.destino = destino;
		this.dataTermino = dataTermino;
		this.ip = ip;
		this.userAgent = userAgent;
		this.identificadorCartao = identificadorCartao;
	}
	
	public LocalDate getDataTermino() {
		return dataTermino;
	}

	@Override
	public String toString() {
		return "Viagem [id=" + id + ", destino=" + destino + ", dataTermino=" + dataTermino + ", dataAviso=" + dataAviso
				+ ", ip=" + ip + ", userAgent=" + userAgent + ", identificadorCartao=" + identificadorCartao + "]";
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
}
