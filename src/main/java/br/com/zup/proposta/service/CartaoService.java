package br.com.zup.proposta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
import br.com.zup.proposta.enums.TipoCarteira;
import br.com.zup.proposta.models.Biometria;
import br.com.zup.proposta.models.Bloqueio;
import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.models.Carteira;
import br.com.zup.proposta.models.Viagem;
import br.com.zup.proposta.repository.BiometriaRepository;
import br.com.zup.proposta.repository.BloqueioRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.repository.CarteiraRepository;
import br.com.zup.proposta.repository.ViagemRepository;
import br.com.zup.proposta.requests.AssociarCarteiraRequest;
import br.com.zup.proposta.requests.AvisoViagemRequest;
import br.com.zup.proposta.requests.CadastroBiometriaRequest;
import br.com.zup.proposta.requests.FeignAssociarCarteiraRequest;
import br.com.zup.proposta.requests.FeignAvisoRequest;
import br.com.zup.proposta.util.AESUtil;
import br.com.zup.proposta.util.ClientIpUtil;
import feign.FeignException;

@Service
@Validated
public class CartaoService {
	
	@Autowired
	private BloqueioRepository bloqueiaRepository;
	@Autowired
	private BiometriaRepository biometriaRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private ViagemRepository viagemRepository;
	@Autowired
	private AccountsService accontsService;
	@Autowired
	private CarteiraRepository carteiraRepository;
	@Autowired
	private AESUtil aesUtil;
	
	private final Logger logger = LoggerFactory.getLogger(CartaoService.class);

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

	public ResponseDto bloquear(String identificador, HttpServletRequest request) {
		
		@NotBlank String ip = ClientIpUtil.get(request);
		@NotBlank String userAgent = request.getHeader("User-Agent");
		
		try {			
			
			Optional<Cartao> optional = this.cartaoRepository.findByIdentificador(identificador);
			
			if (optional.isPresent()) {
				Cartao cartao = optional.get();
				
				Optional<Bloqueio> optionalCancelado = this.bloqueiaRepository.findByNumeroCartao(cartao.getId());
				
				if (optionalCancelado.isPresent()) {
					logger.error("Cartão já foi bloqueado: " + optionalCancelado.get());
					
					return new ResponseDto("Cartão já foi bloquado", HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
				Bloqueio bloqueio = new Bloqueio(ip, userAgent, cartao.getId(), cartao.getIdentificador());
				
				this.bloqueiaRepository.save(bloqueio);
				
				logger.info("Cartão bloqueado com sucesso: " + bloqueio);
				
				return new ResponseDto("Cartão bloqueado com sucesso", HttpStatus.OK);
				
			}
			
			logger.error("Cartão não foi encontrado: IDENTIFICADOR=" + identificador);
			
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

	@Transactional
	public ResponseDto registrarAvisoViagem(@Valid AvisoViagemRequest request, String identificadorCartao,
			HttpServletRequest servlet) {
		
		String ip = ClientIpUtil.get(servlet);
		String userAgent = servlet.getHeader("User-Agent");
		
		Optional<Cartao> optional= this.cartaoRepository.findByIdentificador(identificadorCartao);
		
		if (!optional.isPresent()) {
			return new ResponseDto("cartão não foi encontrado", HttpStatus.NOT_FOUND);
		}
		
		try {
			Viagem viagem = request.toModel(ip, userAgent, identificadorCartao);
			Cartao cartao = optional.get();
			
			FeignAvisoRequest requestAviso = new FeignAvisoRequest(request.getDestino(), viagem.getDataTermino().toString());
			this.accontsService.aviso(optional.get().getId(), requestAviso);
			
			viagem.setCartao(cartao);
			viagem = this.viagemRepository.save(viagem);
	
			cartao.addRegistroViagem(viagem);
			
			this.cartaoRepository.save(cartao);
			logger.info("aviso de viagem registrado com sucesso: "+ viagem);
			
			return new ResponseDto("viagem registrada com sucesso", HttpStatus.OK);
			
		} catch (ConstraintViolationException e) {
			List<FieldErrorDto> listaErros = new ArrayList<FieldErrorDto>();
			
			e.getConstraintViolations().forEach(error -> {
				listaErros.add(new FieldErrorDto(error.getPropertyPath().toString(), error.getMessage()));
			});
			
			logger.error("Dados incompletos para o aviso de viagens: | request: " + request + " | IP: " + ip + " | userAgent: " + userAgent + " | identificador: " + identificadorCartao);
			
			return new ResponseDto("dados incompletos para aviso de viagens", HttpStatus.BAD_REQUEST, listaErros);
		} catch (FeignException e) {
			logger.error("erro ao registrar aviso de viagem: request=" + request + " | identificador=" + identificadorCartao);
			
			return new ResponseDto("não foi possível registrar o aviso de viagem", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Transactional
	public ResponseDto associar(AssociarCarteiraRequest request, String identificador) {
		try {
			Optional<Cartao> optionalCartao = this.cartaoRepository.findByIdentificador(identificador);
			
			if (optionalCartao.isPresent()) {
				Cartao cartao = optionalCartao.get();
				
				Optional<Carteira> optionalCarteira = this.carteiraRepository.findByTipoAndCartaoId(TipoCarteira.valueOf(request.getTipo()), cartao.getId());
				
				if (!optionalCarteira.isPresent()) {
					Carteira carteira = request.toModel(cartao);
					this.accontsService.associar(this.aesUtil.decrypt(cartao.getId()), new FeignAssociarCarteiraRequest(request.getEmail(), request.getTipo()));
					
					carteira = this.carteiraRepository.save(carteira);
					this.cartaoRepository.save(cartao);
					
					logger.info("carteira cadastrada com sucesso");
					
					return new ResponseDto("carteira cadastrada com sucesso", HttpStatus.CREATED, carteira);
				}
				
				logger.error("você já possui dados com esta carteira");
				return new ResponseDto("você já possui dados com esta carteira", HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			logger.error("cartão não foi encontrado");
			return new ResponseDto("cartão não foi encontrado", HttpStatus.NOT_FOUND);
		} catch (FeignException e) {
			e.printStackTrace();
			logger.error("não foi possível criar carteira");
			return new ResponseDto("não foi possível criar carteira", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}
}
