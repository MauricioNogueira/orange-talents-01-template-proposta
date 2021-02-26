package br.com.zup.proposta.requests;

import javax.validation.constraints.NotBlank;

public class BloquearCartaoRequest {

	@NotBlank
	private String ip;
	
	@NotBlank
	private String userAgent;
	
	public BloquearCartaoRequest(String ip, String userAgent) {
		this.ip = ip;
		this.userAgent = userAgent;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}
}
