package br.com.zup.proposta.requests;

import javax.validation.constraints.NotBlank;

public class BloquearCartaoRequest {

	@NotBlank
	private String ipDoido;
	
	@NotBlank
	private String userAgent;
	
	public BloquearCartaoRequest(String ipDoido, String userAgent) {
		this.ipDoido = ipDoido;
		this.userAgent = userAgent;
	}

	public String getIp() {
		return ipDoido;
	}

	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public String toString() {
		return "BloquearCartaoRequest [ip=" + ipDoido + ", userAgent=" + userAgent + "]";
	}
}
