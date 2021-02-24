package br.com.zup.proposta.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.proposta.enums.StatusSolicitacao;

public class ResponseConsultaDoSolicitanteDto {
	private final Logger logger = LoggerFactory.getLogger(ResponseConsultaDoSolicitanteDto.class);
	
	private String documento;
	private String nome;
	private String resultadoSolicitacao;
	private String idProposta;

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public String getIdProposta() {
		return idProposta;
	}

	public String getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}

	@Override
	public String toString() {
		return "ResponseConsultaDoSolicitanteDto [documento=" + documento + ", nome=" + nome + ", resutadoSolicitacao="
				+ resultadoSolicitacao + ", idProposta=" + idProposta + "]";
	}
	
	public boolean verificaSePropostaTemRestricao() {
		
		if (this.resultadoSolicitacao.equals(StatusSolicitacao.COM_RESTRICAO)) {
			return true;
		}
		
		return false;
	}
	
	public StatusSolicitacao getResultStatusSolicitacao() {
		return StatusSolicitacao.valueOf(this.getResultadoSolicitacao());
	}
	
	public void converteJson(ObjectMapper mapper, String json) {
		try {
			this.resultadoSolicitacao = mapper.readValue(json, this.getClass()).getResultadoSolicitacao();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("Erro ao pegar status da solicitação da proposta");
		}
	}
}
