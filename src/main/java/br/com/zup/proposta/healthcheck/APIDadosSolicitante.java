package br.com.zup.proposta.healthcheck;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class APIDadosSolicitante implements HealthIndicator {
	
	@Value("${api.analise}")
	private String uriAnalise;

	@Override
	public Health health() {
		Map<String, Object> details = new HashMap<>();
		
		if (checkApiConsulta()) {
			details.put("descricao", "API de consulta de proposta funcionando");
			
			return Health.status(Status.UP).withDetails(details).build();
		}
		
		details.put("descricao", "API não está funcionando");
		
		return Health.status(Status.DOWN).withDetails(details).build();
	}
	
	public boolean checkApiConsulta() {
		boolean flag = false;
		
		try {
			URL url = new URL(this.uriAnalise);
			URLConnection connection = url.openConnection();
			connection.connect();
			
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		
		return flag;
	}
}