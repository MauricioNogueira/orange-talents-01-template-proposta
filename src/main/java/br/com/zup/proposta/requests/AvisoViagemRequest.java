package br.com.zup.proposta.requests;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.models.Viagem;

public class AvisoViagemRequest {

	@NotBlank
	private String destino;
	
	@NotNull
	@Future
	private LocalDate dataTermino;
	
	public AvisoViagemRequest(String destino, LocalDate dataTermino) {
		this.destino = destino;
		this.dataTermino = dataTermino;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	@Override
	public String toString() {
		return "AvisoViagemRequest [destino=" + destino + ", dataTermino=" + dataTermino + "]";
	}

	public Viagem toModel(String ip, String userAgent, String identificadorCartao) {
		return new Viagem(this.destino, this.dataTermino, ip, userAgent, identificadorCartao);
	}
}
