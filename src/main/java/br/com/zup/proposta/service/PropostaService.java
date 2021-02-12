package br.com.zup.proposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.dto.ResponseConsultaDoSolicitanteDto;
import br.com.zup.proposta.exceptions.FeignCustomExcepition;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.repository.PropostaRepository;
import br.com.zup.proposta.requests.CadastroPropostaRequest;
import br.com.zup.proposta.requests.ConsultaRequest;
import feign.FeignException;

@Service
public class PropostaService {
	
	private final ConsultaDadosDoSolicitanteService consultaDadosDoSolicitanteService;
	private final PropostaRepository propostaRepository;
	private final Logger logger = LoggerFactory.getLogger(PropostaService.class);
	
	public PropostaService(ConsultaDadosDoSolicitanteService consultaDadosDoSolicitanteService,
			PropostaRepository propostaRepository) {
		this.consultaDadosDoSolicitanteService = consultaDadosDoSolicitanteService;
		this.propostaRepository = propostaRepository;
	}

	public Proposta cadastrar(CadastroPropostaRequest request) {
		try {
			
			Proposta proposta = request.toModel(this.propostaRepository);
			
			ResponseConsultaDoSolicitanteDto response = this.consultaDadosDoSolicitanteService.consultar(new ConsultaRequest(request.getDocumento(), request.getNome(), null));
			
			if (response.verificaSePropostaTemRestricao()) {
				logger.warn("proposta com restrição: "+response);
				
				throw new IllegalAccessError("Erro pra ser corrigido aqui");
			}
			
			logger.info("Proposta cadastrada: "+response);
			
			return this.propostaRepository.save(proposta);
		} catch (FeignException e) {
			
			if (e.status() == 422) {
				logger.warn("documento não foi encontrado: "+e.contentUTF8());
				
				throw new FeignCustomExcepition("documento solicitado não foi encontrado", HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			logger.error("Erro interno no sistema: " + e.contentUTF8());
			throw new FeignCustomExcepition("Erro no sistema", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
