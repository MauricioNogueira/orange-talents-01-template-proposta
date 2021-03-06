package br.com.zup.proposta.requests;

public class FeignAvisoRequest {

	private String destino;
	private String validoAte;
	
	public FeignAvisoRequest(String destino, String validoAte) {
		this.destino = destino;
		this.validoAte = validoAte;
	}

	public String getDestino() {
		return destino;
	}

	public String getValidoAt() {
		return validoAte;
	}

	@Override
	public String toString() {
		return "FeignAvisoRequest [destino=" + destino + ", validoAte=" + validoAte + "]";
	}
}
