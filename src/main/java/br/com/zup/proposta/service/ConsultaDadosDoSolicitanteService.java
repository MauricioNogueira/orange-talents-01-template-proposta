package br.com.zup.proposta.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.dto.ResponseConsultaDoSolicitanteDto;
import br.com.zup.proposta.requests.ConsultaRequest;

@FeignClient(url = "${api.analise}", name = "consulta")
public interface ConsultaDadosDoSolicitanteService {

	@RequestMapping(method = RequestMethod.POST, value = "/solicitacao")
	public ResponseConsultaDoSolicitanteDto consultar(ConsultaRequest request);
}
