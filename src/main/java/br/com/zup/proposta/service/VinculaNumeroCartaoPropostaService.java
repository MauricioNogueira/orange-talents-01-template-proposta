package br.com.zup.proposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.repository.VencimentoRepository;
import br.com.zup.proposta.response.DadosCartaoResponse;
import feign.FeignException;

@Service
public class VinculaNumeroCartaoPropostaService {

	private final AccountsService accountsService;
	private final CartaoRepository cartaoRepository;
	private final VencimentoRepository vencimentoRepository;
	private final Logger logger = LoggerFactory.getLogger(VinculaNumeroCartaoPropostaService.class);
	
	public VinculaNumeroCartaoPropostaService(
			AccountsService accountsService,
			CartaoRepository cartaoRepository,
			VencimentoRepository vencimentoRepository
	) {
		this.accountsService = accountsService;
		this.cartaoRepository = cartaoRepository;
		this.vencimentoRepository = vencimentoRepository;
	}
	
	public Proposta vincular(Proposta proposta) {
		
		try {
			DadosCartaoResponse response = this.accountsService.verificarCartao(proposta.getId());
			
			Cartao cartao = response.toModel(proposta, this.vencimentoRepository);
			
			cartao = this.cartaoRepository.save(cartao);
			
			logger.info("Cartão foi vinculado | " + proposta);
			
			return proposta;
		} catch (FeignException e) {
			logger.error("Cartão não foi encontrado para a proposta: " + proposta);
		}
		
		return null;
	}
}
