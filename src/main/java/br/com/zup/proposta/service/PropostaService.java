package br.com.zup.proposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	private final ObjectMapper mapper;
	
	public PropostaService(ConsultaDadosDoSolicitanteService consultaDadosDoSolicitanteService,
			PropostaRepository propostaRepository, ObjectMapper mapper) {
		this.consultaDadosDoSolicitanteService = consultaDadosDoSolicitanteService;
		this.propostaRepository = propostaRepository;
		this.mapper = mapper;
	}

	public Proposta cadastrar(CadastroPropostaRequest request) {
		
		Proposta proposta = request.toModel(this.propostaRepository);
		ResponseConsultaDoSolicitanteDto response = null;
		
		try {
			
			response = this.consultaDadosDoSolicitanteService.consultar(new ConsultaRequest(request.getDocumento(), request.getNome(), null));
			
		} catch (FeignException e) {
			
			if (e.status() == 422) {
				logger.warn("documento não foi encontrado: "+e.contentUTF8());
				String json = e.contentUTF8();
				
				response = new ResponseConsultaDoSolicitanteDto();
				response.converteJson(mapper, json);
			} else {				
				logger.error("Erro interno no sistema: " + e.getMessage());
				throw new FeignCustomExcepition("Erro no sistema", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		proposta.setStatus(response.getResultStatusSolicitacao());
		
		logger.info("Proposta cadastrada: "+response);
		
		return this.propostaRepository.save(proposta);
	}
}