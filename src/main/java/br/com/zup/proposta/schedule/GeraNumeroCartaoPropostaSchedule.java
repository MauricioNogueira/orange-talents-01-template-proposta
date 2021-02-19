package br.com.zup.proposta.schedule;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.enums.StatusSolicitacao;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.repository.PropostaRepository;
import br.com.zup.proposta.service.VinculaNumeroCartaoPropostaService;

@Service
@Profile("dev")
@EnableAsync
@EnableScheduling
public class GeraNumeroCartaoPropostaSchedule {
	
	private final VinculaNumeroCartaoPropostaService vinculaNumeroCartaoPropostaService;
	private final PropostaRepository propostaRepository;
	
	public GeraNumeroCartaoPropostaSchedule(
			VinculaNumeroCartaoPropostaService vinculaNumeroCartaoPropostaService,
			PropostaRepository propostaRepository
	) {
		this.vinculaNumeroCartaoPropostaService = vinculaNumeroCartaoPropostaService;
		this.propostaRepository = propostaRepository;
	}

	@Scheduled(fixedDelay = 5000)
	public void teste() {
		Pageable pageable = PageRequest.of(0, 1000);
		Page<Proposta> lista = this.propostaRepository.findByStatusAndCartaoIsNull(StatusSolicitacao.SEM_RESTRICAO.getValue(), pageable);
		
		
		lista.forEach(proposta -> {
			this.vinculaNumeroCartaoPropostaService.vincular(proposta);
		});
		
	}
}