package br.com.zup.proposta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.models.Bloqueio;

public interface BloqueiaRepository extends JpaRepository<Bloqueio, Long> {

	Optional<Bloqueio> findByNumeroCartao(String id);
	
}
