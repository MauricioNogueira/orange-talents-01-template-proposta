package br.com.zup.proposta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.models.Cancelado;

public interface CanceladoRepository extends JpaRepository<Cancelado, Long> {

	Optional<Cancelado> findByNumeroCartao(String id);
	
}
