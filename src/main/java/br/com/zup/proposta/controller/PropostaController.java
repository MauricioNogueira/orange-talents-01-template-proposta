package br.com.zup.proposta.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.dto.PropostaDto;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.requests.CadastroPropostaRequest;
import br.com.zup.proposta.service.PropostaService;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {
	
	private final PropostaService propostaService;
	
	public PropostaController(PropostaService propostaService) {
		this.propostaService = propostaService;
	}

	@PostMapping
	public ResponseEntity<PropostaDto> cadastrar(@RequestBody @Valid CadastroPropostaRequest request, UriComponentsBuilder uriComponentsBuilder) {		
		
		Proposta proposta = this.propostaService.cadastrar(request);
		
		URI location = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(location).body(new PropostaDto(proposta));
	}
}
