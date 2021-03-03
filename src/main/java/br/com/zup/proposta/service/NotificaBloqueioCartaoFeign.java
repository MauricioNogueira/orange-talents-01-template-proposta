package br.com.zup.proposta.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.requests.NotificaBloqueioCartaoRequest;
import br.com.zup.proposta.response.NotificacaoBloqueioResponse;

@FeignClient(url = "${api.accounts}", name = "accounts-bloqueio")
public interface NotificaBloqueioCartaoFeign {

	@RequestMapping(method = RequestMethod.POST, value = "/cartoes/{id}/bloqueios")
	public NotificacaoBloqueioResponse notificar(@PathVariable("id") String id, NotificaBloqueioCartaoRequest request);
}
