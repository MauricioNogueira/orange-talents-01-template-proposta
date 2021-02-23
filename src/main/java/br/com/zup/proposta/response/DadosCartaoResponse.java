package br.com.zup.proposta.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.models.Vencimento;
import br.com.zup.proposta.repository.VencimentoRepository;

public class DadosCartaoResponse {
	
	private final Logger logger = LoggerFactory.getLogger(DadosCartaoResponse.class);

	@NotBlank
	private String id;
	
	@NotNull
	private LocalDateTime emitidoEm;
	
	@NotBlank
	private String titular;
	
	@NotNull
	private BigDecimal limite;
	
	@NotNull
	private VencimentoResponse vencimento;
	
	@NotBlank
	private Long idProposta;

	public DadosCartaoResponse(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite,
			VencimentoResponse vencimento, Long idProposta) {
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.limite = limite;
		this.vencimento = vencimento;
		this.idProposta = idProposta;
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

	public BigDecimal getLimite() {
		return limite;
	}

	public VencimentoResponse getVencimento() {
		return vencimento;
	}

	public Long getIdProposta() {
		return idProposta;
	}

	@Override
	public String toString() {
		return "ResponseDadosCartaoDto [id=" + id + ", emitidoEm=" + emitidoEm + ", titular=" + titular + ", limite="
				+ limite + ", vencimento=" + vencimento + ", idProposta=" + idProposta + "]";
	}

	public Cartao toModel(Proposta proposta, VencimentoRepository vencimentoRepository) {
		Vencimento vencimento = this.getVencimento().toModel(vencimentoRepository);
		
		Cartao cartao = new Cartao(this.id, this.emitidoEm, this.titular, proposta, vencimento);
		
		logger.info("Cartão cadastrado: " + cartao);
		
		return cartao;
	}
}
