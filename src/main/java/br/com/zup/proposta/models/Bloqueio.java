package br.com.zup.proposta.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String numeroCartao;
	
	@NotBlank
	private String ip;
	
	@NotBlank
	private String userAgent;
	private LocalDateTime data = LocalDateTime.now();
	
	private boolean notificated = false;
	
	@NotBlank
	private String identificador;
	
	@Deprecated
	public Bloqueio() {}
	
	public Bloqueio(@NotBlank String ip, @NotBlank String userAgent, @NotBlank String numeroCartao, @NotBlank String identificador) {
		this.ip = ip;
		this.userAgent = userAgent;
		this.numeroCartao = numeroCartao;
		this.identificador = identificador;
	}

	public Long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public LocalDateTime getData() {
		return data;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}
	
	public boolean getNotificated() {
		return notificated;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	
	public void setNotificated(boolean notificated) {
		this.notificated = notificated;
	}

	@Override
	public String toString() {
		return "Bloqueio [id=" + id + ", numeroCartao=" + numeroCartao + ", ip=" + ip + ", userAgent=" + userAgent
				+ ", data=" + data + ", identificador=" + identificador + "]";
	}
}
