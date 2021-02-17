package br.com.zup.proposta.enums;

public enum StatusSolicitacao {
	COM_RESTRICAO("NAO_ELEGIVEL"),
	SEM_RESTRICAO("ELEGIVEL");
	
	private String value;
	
	StatusSolicitacao(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}