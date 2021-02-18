package br.com.zup.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.models.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
	
}
