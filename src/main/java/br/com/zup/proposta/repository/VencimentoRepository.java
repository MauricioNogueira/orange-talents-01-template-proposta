package br.com.zup.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.models.Vencimento;

public interface VencimentoRepository extends JpaRepository<Vencimento, String> {

}
