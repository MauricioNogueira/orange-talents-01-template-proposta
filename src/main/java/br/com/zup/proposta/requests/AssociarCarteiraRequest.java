package br.com.zup.proposta.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.zup.proposta.enums.TipoCarteira;
import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.models.Carteira;

public class AssociarCarteiraRequest {

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String tipo;
	
	public AssociarCarteiraRequest(String email, String tipo) {
		this.email = email;
		this.tipo = tipo;
	}

	public String getEmail() {
		return email;
	}

	public String getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		return "AssociarCarteiraRequest [email=" + email + ", tipo=" + tipo + "]";
	}

	public Carteira toModel(Cartao cartao) {
		return new Carteira(email, cartao, TipoCarteira.valueOf(tipo));
	}
}
