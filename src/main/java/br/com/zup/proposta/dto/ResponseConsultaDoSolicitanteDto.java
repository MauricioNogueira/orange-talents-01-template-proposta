package br.com.zup.proposta.dto;

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
}
