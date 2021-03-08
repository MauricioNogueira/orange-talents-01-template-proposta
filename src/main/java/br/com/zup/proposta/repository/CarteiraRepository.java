package br.com.zup.proposta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.enums.TipoCarteira;
import br.com.zup.proposta.models.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
	Optional<Carteira> findByTipoAndCartaoId(TipoCarteira tipo, String id);
}
