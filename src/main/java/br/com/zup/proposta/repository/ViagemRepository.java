package br.com.zup.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.models.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
	
}
