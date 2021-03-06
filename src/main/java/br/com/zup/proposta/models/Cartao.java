package br.com.zup.proposta.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.enums.StatusCartao;

@Entity
public class Cartao {

	@Id
	private String id;
	
	@NotNull
	private LocalDateTime emitidoEm;
	
	@NotBlank
	private String titular;
	
	@NotNull
	@OneToOne(cascade = CascadeType.PERSIST)
	private Proposta proposta;
	
	@NotNull
	@OneToOne(cascade = CascadeType.PERSIST)
	private Vencimento vencimento;
	
	@OneToMany(mappedBy = "cartao")
	private List<Biometria> biometria;
	
	@Enumerated(EnumType.STRING)
	private StatusCartao status;
	
	private String identificador;
	
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST)
	private List<Viagem> viagens;
	
	@Deprecated
	public Cartao() {}
	
	public Cartao(String id, LocalDateTime emitidoEm, String titular, Proposta proposta, Vencimento vencimento) {
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.proposta = proposta;
		this.vencimento = vencimento;
		this.identificador = UUID.randomUUID().toString();
	}
	
	public void setStatus(StatusCartao status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public Proposta getProposta() {
		return proposta;
	}
	
	public String getIdentificador() {
		return this.identificador;
	}
	
	public List<Viagem> getViagens() {
		return viagens;
	}
	
	public void addRegistroViagem(Viagem viagem) {
		viagens.add(viagem);
	}

	@Override
	public String toString() {
		return "Cartao [id=" + id + ", emitidoEm=" + emitidoEm + ", titular=" + titular + ", identificador=" + identificador +"]";
	}
}
