package br.com.zup.proposta.util;

import javax.servlet.http.HttpServletRequest;

public class ClientIpUtil {

	public static String get(HttpServletRequest request) {
		
		if (request.getHeader("X-Forwarded-For") != null) {
			return request.getHeader("X-Forwarded-For");
		}
		
		return request.getRemoteAddr();
	}
}