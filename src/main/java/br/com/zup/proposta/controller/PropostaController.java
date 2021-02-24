package br.com.zup.proposta.controller;

import java.net.URI;

import javax.crypto.spec.IvParameterSpec;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.dto.PropostaDto;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.requests.CadastroPropostaRequest;
import br.com.zup.proposta.service.PropostaService;
import br.com.zup.proposta.util.AESUtil;

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
		
		URI location = uriComponentsBuilder.path("/api/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(location).body(new PropostaDto(proposta));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PropostaDto> acompanhar(@PathVariable("id") Long id) {
		PropostaDto propostaDto = this.propostaService.findById(id);
		
		if (propostaDto == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(propostaDto);
	}
}
