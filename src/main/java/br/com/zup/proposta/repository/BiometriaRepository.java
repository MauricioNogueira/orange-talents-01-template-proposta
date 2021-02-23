package br.com.zup.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.models.Biometria;

public interface BiometriaRepository extends JpaRepository<Biometria, Long> {
	
}
