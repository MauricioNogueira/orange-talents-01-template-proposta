package br.com.zup.proposta.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.proposta.repository.PropostaRepository;
import br.com.zup.proposta.requests.CadastroPropostaRequest;
import br.com.zup.proposta.utils.CriaRequestCadastroProposta;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("test")
class PropostaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@AfterEach
	public void afterEach() {
		this.propostaRepository.deleteAll();
	}

	@Test
	@DisplayName("Deve realizar o cadastro da proposta")
	void deveCadastrarUmaProposta() throws Exception {
		CadastroPropostaRequest request = new CriaRequestCadastroProposta()
				.nome("fulano")
				.email("fulano@email.com")
				.documento("849.197.810-08")
				.endereco("Em algum lugar neste mundo")
				.salario(new BigDecimal("2500"))
				.build();
		
		String json = mapper.writeValueAsString(request);
		
		mockMvc.perform(post("/api/propostas")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Não deve cadastrar a proposta com número de documento igual")
	void naoDeveCadastrarPropostaComDocumentoIgual() throws Exception {
		CadastroPropostaRequest request = new CriaRequestCadastroProposta()
				.nome("fulano")
				.email("fulano@email.com")
				.documento("849.197.810-08")
				.endereco("Em algum lugar neste mundo")
				.salario(new BigDecimal("2500"))
				.build();
		
		this.propostaRepository.save(request.toModel(propostaRepository));
		
		CadastroPropostaRequest request2 = new CriaRequestCadastroProposta()
				.nome("fulano")
				.email("fulano@email.com")
				.documento("849.197.810-08")
				.endereco("Em algum lugar neste mundo")
				.salario(new BigDecimal("2500"))
				.build();
		
		String json = mapper.writeValueAsString(request2);
		
		mockMvc.perform(post("/api/propostas")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	@DisplayName("Não deve cadastrar a proppsta com campos inválidos")
	void naoDeveCadastrarComCamposInvalidos() throws Exception {
		CadastroPropostaRequest request = new CriaRequestCadastroProposta()
				.nome("")
				.email("")
				.documento("")
				.endereco("")
				.salario(null)
				.build();
		
		String json = mapper.writeValueAsString(request);
		
		mockMvc.perform(post("/api/propostas")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest());
	}
}
