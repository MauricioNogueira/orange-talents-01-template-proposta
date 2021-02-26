package br.com.zup.proposta.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.dto.BiometriaDto;
import br.com.zup.proposta.dto.ResponseDto;
import br.com.zup.proposta.models.Biometria;
import br.com.zup.proposta.models.Cancelado;
import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.repository.BiometriaRepository;
import br.com.zup.proposta.repository.CanceladoRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.requests.CadastroBiometriaRequest;
import br.com.zup.proposta.util.AESUtil;
import br.com.zup.proposta.util.ClientIpUtil;

@Service
public class CartaoService {
	
	@Autowired
	private AESUtil aesUtil;
	
	@Autowired
	private CanceladoRepository canceladoRepository;
	
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
		
		String ip = ClientIpUtil.get(request);
		String userAgent = request.getHeader("User-Agent");
		
		try {			
			
			Optional<Cartao> optional = this.cartaoRepository.findById(idCartao);
			
			if (optional.isPresent()) {
				Cartao cartao = optional.get();
				
				Optional<Cancelado> optionalCancelado = this.canceladoRepository.findByNumeroCartao(cartao.getId());
				
				if (optionalCancelado.isPresent()) {
					logger.error("Cartão já foi cancelado: " + optionalCancelado.get());
					
					return new ResponseDto("Cartão já foi cancelado", HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
				Cancelado cancelado = new Cancelado(ip, userAgent, cartao.getId());
				
				this.canceladoRepository.save(cancelado);
				
				logger.info("Cartão cancelado com sucesso: " + cancelado);
				
				return new ResponseDto("Cartão cancelado com sucesso", HttpStatus.OK);
				
			}
			
			logger.error("Cartão não foi encontrado: " + optional.get());
			
			return new ResponseDto("Cartão não foi encontrado", HttpStatus.NOT_FOUND);
		} catch (ConstraintViolationException e) {
			logger.error("Dados incompleto para cadastrar o cancelamento: IP=" + ip + " , User-Agent=" + userAgent);
			return new ResponseDto("Dados incompleto", HttpStatus.BAD_REQUEST);
		}
		
	}
}
