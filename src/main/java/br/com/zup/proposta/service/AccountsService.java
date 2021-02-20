package br.com.zup.proposta.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zup.proposta.response.DadosCartaoResponse;

@FeignClient(url = "${api.accounts}", name = "accounts")
public interface AccountsService {

	@RequestMapping(method = RequestMethod.GET, value = "/cartoes")
	public DadosCartaoResponse verificarCartao(@RequestParam("idProposta") Long idProposta);
}
