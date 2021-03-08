package br.com.zup.proposta.requests;

public class FeignAssociarCarteiraRequest {

	private String email;
	private String carteira;

	public FeignAssociarCarteiraRequest(String email, String carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public String getCarteira() {
		return carteira;
	}
}
