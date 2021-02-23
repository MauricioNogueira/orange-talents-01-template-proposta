package br.com.zup.proposta.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.dto.BiometriaDto;
import br.com.zup.proposta.models.Biometria;
import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.repository.BiometriaRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.requests.CadastroBiometriaRequest;

@Service
public class CartaoService {
	
	private final BiometriaRepository biometriaRepository;
	private final CartaoRepository cartaoRepository;
	private final Logger logger = LoggerFactory.getLogger(CartaoService.class);
	
	public CartaoService(BiometriaRepository biometriaRepository, CartaoRepository cartaoRepository) {
		this.biometriaRepository = biometriaRepository;
		this.cartaoRepository = cartaoRepository;
	}

	public BiometriaDto cadastrar(CadastroBiometriaRequest request, String idCartao) {
		Optional<Cartao> optional = this.cartaoRepository.findByUuid(idCartao);
		
		if (optional.isPresent()) {
			Biometria biometria = request.toModel(optional.get());
			
			biometria = this.biometriaRepository.save(biometria);
			
			logger.info("Biometria criada com sucesso!");
			
			return new BiometriaDto(biometria);
		}
		
		logger.error("Cartão não foi encontrado");
		
		return null;
	}
}
