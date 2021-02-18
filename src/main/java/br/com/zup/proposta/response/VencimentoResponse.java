package br.com.zup.proposta.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.zup.proposta.models.Vencimento;
import br.com.zup.proposta.repository.VencimentoRepository;

public class VencimentoResponse {
	
	private final Logger logger = LoggerFactory.getLogger(VencimentoResponse.class);

	@NotBlank
	private String id;
	
	@NotNull
	@Positive
	private int dia;
	
	@NotNull
	private LocalDateTime dataDeCriacao;

	public VencimentoResponse(String id, int dia, LocalDateTime dataDeCriacao) {
		this.id = id;
		this.dia = dia;
		this.dataDeCriacao = dataDeCriacao;
	}

	public String getId() {
		return id;
	}

	public int getDia() {
		return dia;
	}

	public LocalDateTime getDataCriacao() {
		return dataDeCriacao;
	}

	@Override
	public String toString() {
		return "CadastraVencimentoRequest [id=" + id + ", dia=" + dia + ", dataCriacao=" + dataDeCriacao + "]";
	}

	public Vencimento toModel(VencimentoRepository vencimentoRepository) {
		Vencimento vencimento = vencimentoRepository.save(new Vencimento(this.id, this.dia, this.dataDeCriacao));
		
		logger.info("Vencimento cadastrado: " + vencimento);
		
		return vencimento;
	}
}
