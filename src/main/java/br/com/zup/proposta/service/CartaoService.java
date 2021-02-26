package br.com.zup.proposta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.zup.proposta.dto.BiometriaDto;
import br.com.zup.proposta.dto.FieldErrorDto;
import br.com.zup.proposta.dto.ResponseDto;
import br.com.zup.proposta.models.Biometria;
import br.com.zup.proposta.models.Bloqueio;
import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.repository.BiometriaRepository;
import br.com.zup.proposta.repository.BloqueiaRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.requests.CadastroBiometriaRequest;
import br.com.zup.proposta.util.ClientIpUtil;

@Service
@Validated
public class CartaoService {
	
	@Autowired
	private BloqueiaRepository bloqueiaRepository;
	
	private final BiometriaRepository biometriaRepository;
	private final CartaoRepository cartaoRepository;
	private final Logger logger = LoggerFactory.getLogger(CartaoService.class);
	
	public CartaoService(BiometriaRepository biometriaRepository, CartaoRepository cartaoRepository) {
		this.biometriaRepository = biometriaRepository;
		this.cartaoRepository = cartaoRepository;
	}

	@Transactional
	public BiometriaDto cadastrar(CadastroBiometriaRequest request, String idCartao) {
		Optional<Cartao> optional = this.cartaoRepository.findByNumeroCartao(idCartao);
		
		if (optional.isPresent()) {
			Biometria biometria = request.toModel(optional.get());
			
			biometria = this.biometriaRepository.save(biometria);
			
			logger.info("Biometria criada com sucesso!");
			
			return new BiometriaDto(biometria);
		}
		
		logger.error("Cartão não foi encontrado");
		
		return null;
	}

	public ResponseDto bloquear(String idCartao, HttpServletRequest request) {
		
		@NotBlank String ip = ClientIpUtil.get(request);
		@NotBlank String userAgent = request.getHeader("User-Agent");
		
		try {			
			
			Optional<Cartao> optional = this.cartaoRepository.findById(idCartao);
			
			if (optional.isPresent()) {
				Cartao cartao = optional.get();
				
				Optional<Bloqueio> optionalCancelado = this.bloqueiaRepository.findByNumeroCartao(cartao.getId());
				
				if (optionalCancelado.isPresent()) {
					logger.error("Cartão já foi bloqueado: " + optionalCancelado.get());
					
					return new ResponseDto("Cartão já foi bloquado", HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
				Bloqueio cancelado = new Bloqueio(ip, userAgent, cartao.getId());
				
				this.bloqueiaRepository.save(cancelado);
				
				logger.info("Cartão bloqueado com sucesso: " + cancelado);
				
				return new ResponseDto("Cartão bloqueado com sucesso", HttpStatus.OK);
				
			}
			
			logger.error("Cartão não foi encontrado: ID=" + idCartao);
			
			return new ResponseDto("Cartão não foi encontrado", HttpStatus.NOT_FOUND);
		} catch (ConstraintViolationException e) {
			List<FieldErrorDto> listaErros = new ArrayList<FieldErrorDto>();
			
			e.getConstraintViolations().forEach(error -> {
				listaErros.add(new FieldErrorDto(error.getPropertyPath().toString(), error.getMessage()));
			});
			
			logger.error("Dados incompleto para registrar o bloqueia: IP=" + ip + " , User-Agent=" + userAgent);
			
			return new ResponseDto("Não foi possível bloquear", HttpStatus.BAD_REQUEST, listaErros);
		}
		
	}
}
