package br.com.zup.proposta.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.dto.PropostaDto;
import br.com.zup.proposta.dto.ResponseConsultaDoSolicitanteDto;
import br.com.zup.proposta.enums.StatusSolicitacao;
import br.com.zup.proposta.exceptions.DataIntegrityException;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.repository.PropostaRepository;
import br.com.zup.proposta.requests.CadastroPropostaRequest;
import br.com.zup.proposta.requests.ConsultaRequest;
import br.com.zup.proposta.service.ConsultaDadosDoSolicitanteService;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
	
	private final PropostaRepository propostaRepository;
	private final ConsultaDadosDoSolicitanteService consultaDadosService;
	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);
	
	public PropostaController(PropostaRepository propostaRepository, ConsultaDadosDoSolicitanteService consultaDadosService) {
		this.propostaRepository = propostaRepository;
		this.consultaDadosService = consultaDadosService;
	}

	@PostMapping
	public ResponseEntity<PropostaDto> cadastrar(@RequestBody @Valid CadastroPropostaRequest request, UriComponentsBuilder uriComponentsBuilder) {		
		Optional<Proposta> optional = this.propostaRepository.findByDocumento(request.getDocumento());
		
		ResponseConsultaDoSolicitanteDto response = this.consultaDadosService.consultar(new ConsultaRequest(request.getDocumento(), request.getNome(), null));
		
		if (optional.isPresent()) {
			logger.info("documento ");
			throw new DataIntegrityException("documento já existe", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (response.getResultadoSolicitacao().equals(StatusSolicitacao.COM_RESTRICAO)) {
			throw new IllegalAccessError("Erro pra ser corrigido aqui");
		}
		
		Proposta proposta = request.toModel();
		
		this.propostaRepository.save(proposta);
		
		URI location = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(location).body(new PropostaDto(proposta));
	}
}
