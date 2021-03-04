package br.com.zup.proposta.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.dto.BiometriaDto;
import br.com.zup.proposta.dto.ResponseDto;
import br.com.zup.proposta.requests.AvisoViagemRequest;
import br.com.zup.proposta.requests.CadastroBiometriaRequest;
import br.com.zup.proposta.service.CartaoService;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {
	
	@Autowired
	private CartaoService cartaoService;
	
	@PostMapping("/{id}/biometria")
	public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastroBiometriaRequest request, @PathVariable("id") String idCartao, UriComponentsBuilder uriComponentBuilder) {
		BiometriaDto biometriaDto = this.cartaoService.cadastrar(request, idCartao);
		
		if (biometriaDto == null) {
			return ResponseEntity.notFound().build();
		}
		
		URI location = uriComponentBuilder.path("/api/cartoes/{id}/biometria").buildAndExpand(biometriaDto.getId()).toUri();
		
		return ResponseEntity.created(location).body(biometriaDto);
	}
	
	@PostMapping("/{id}/bloquear")
	public ResponseEntity<ResponseDto> bloquear(@PathVariable("id") String idCartao, HttpServletRequest request) {
		
		ResponseDto response = this.cartaoService.bloquear(idCartao, request);
		
		return ResponseEntity.status(response.getStatus()).body(response);		
	}
	
	@PostMapping("/{id}/aviso-viagem")
	public ResponseEntity<ResponseDto> avisoViagem(@RequestBody @Valid AvisoViagemRequest request, @PathVariable("id") String identificadorCartao, HttpServletRequest servlet) {
		
		ResponseDto response = this.cartaoService.registrarAvisoViagem(request, identificadorCartao, servlet);
		
		return ResponseEntity.status(response.getStatus()).body(response);
	}
}
