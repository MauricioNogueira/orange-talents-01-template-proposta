package br.com.zup.proposta.dto;

import br.com.zup.proposta.enums.StatusSolicitacao;

public class ResponseConsultaDoSolicitanteDto {

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
}
