package br.com.zup.proposta.requests;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import br.com.zup.proposta.exceptions.DataIntegrityException;
import br.com.zup.proposta.models.Proposta;
import br.com.zup.proposta.repository.PropostaRepository;
import br.com.zup.proposta.validations.CPFOuCNPJ;


public class CadastroPropostaRequest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@NotBlank
	@CPFOuCNPJ
	private String documento;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String endereco;
	
	@NotNull
	@Positive
	private BigDecimal salario;

	public CadastroPropostaRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public String getDocumento() {
		return documento;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}
	
	public Proposta toModel(PropostaRepository propostaRepository) {
		
		Optional<Proposta> optional = propostaRepository.findByDocumento(documento);
		
		if (optional.isPresent()) {
			logger.warn("Documento informado já existe na base de dados: " + documento);
			
			throw new DataIntegrityException("documento já existe", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salario);
	}
}
