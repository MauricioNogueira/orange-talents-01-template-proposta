package br.com.zup.proposta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.models.Bloqueio;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {

	Optional<Bloqueio> findByNumeroCartao(String id);

	List<Bloqueio> findTop200ByNotificatedFalse();
	
}
