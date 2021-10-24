package br.com.alura.livraria.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("teste")
@Transactional
class AutorControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void naoDeveriaCadastrarAutorComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveriaCadastrarAutorComEmailsIguais() throws Exception {
		String json = "{\"nome\": \"Um Autor\","
				+ "\"email\": \"umemail@email.com\","
				+ "\"dataNascimento\": \"21/10/2021\","
				+ "\"miniCurriculo\": \"Algum currículo só para funcionar\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json));
		
		String json2 = "{\"nome\": \"Outro Autor\","
				+ "\"email\": \"umemail@email.com\","
				+ "\"dataNascimento\": \"21/10/2021\","
				+ "\"miniCurriculo\": \"Algum currículo só para funcionar2\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json2))
			.andExpect(MockMvcResultMatchers.status().isInternalServerError()); //bad request
	}
	
	@Test
	void deveriaCastararAutorComDadosCompletos() throws Exception{
		String json = "{\"nome\": \"Um Autor\","
				+ "\"email\": \"umemail@email.com\","
				+ "\"dataNascimento\": \"21/10/2021\","
				+ "\"miniCurriculo\": \"Algum currículo só para funcionar\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.header().exists("Location"))
			.andExpect(MockMvcResultMatchers.content().json(json));
	}
	
	

}
