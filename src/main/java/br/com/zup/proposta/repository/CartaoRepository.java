package br.com.zup.proposta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zup.proposta.models.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

	@Query("SELECT C FROM Cartao C WHERE C.id = :idCartao")
	Optional<Cartao> findByNumeroCartao(String idCartao);
	
	Optional<Cartao> findById(String idCartao);
}
