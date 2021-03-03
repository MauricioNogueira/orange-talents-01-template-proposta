package br.com.zup.proposta.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.models.Bloqueio;
import br.com.zup.proposta.models.Cartao;
import br.com.zup.proposta.repository.BloqueioRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.requests.NotificaBloqueioCartaoRequest;
import br.com.zup.proposta.response.NotificacaoBloqueioResponse;
import br.com.zup.proposta.service.NotificaBloqueioCartaoFeign;

@Service
@Profile(value = {"dev", "prod"})
@EnableAsync
@EnableScheduling
public class NotificarBloqueioCartaoSchedule {
	
	private final Logger logger = LoggerFactory.getLogger(NotificarBloqueioCartaoSchedule.class);
	
	@Autowired
	private BloqueioRepository bloqueioRepositoy;
	
	@Autowired
	private NotificaBloqueioCartaoFeign notificaBloqueioCartao;
	
	@Autowired
	private CartaoRepository cartaoRepository;

	@Scheduled(fixedDelay = 5000)
	public void inicializa() {
		List<Bloqueio> listaBloqueioAtualizada = new ArrayList<>();
		List<Cartao> listaCartaoAtualizado = new ArrayList<>();
		List<Bloqueio> lista = this.bloqueioRepositoy.findTop200ByNotificatedFalse();
		
		try {			
			NotificaBloqueioCartaoRequest request = new NotificaBloqueioCartaoRequest("proposta");
			
			lista.forEach(bloqueio -> {
				bloqueio.setNotificated(true);
				
				Optional<Cartao> optional = this.cartaoRepository.findById(bloqueio.getNumeroCartao());
				
				if (optional.isPresent()) {
					NotificacaoBloqueioResponse response = this.notificaBloqueioCartao.notificar(bloqueio.getNumeroCartao(), request);
					
					Cartao cartao = optional.get();
					cartao.setStatus("BLOQUEADO");
					
					listaBloqueioAtualizada.add(bloqueio);
					listaCartaoAtualizado.add(cartao);
					
					logger.info("resultado: " + response.getResultado());
				}
			});
			
			this.bloqueioRepositoy.saveAll(listaBloqueioAtualizada);
			this.cartaoRepository.saveAll(listaCartaoAtualizado);
			
		} catch (Exception e) {
			logger.error("não foi possível notificar o bloqueio do cartão");
		}
	}
}
