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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.alura.livraria.dto.AutorOutputDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("teste")
@Transactional
class LivroControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception{
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveriaCadastrarLivroComAutorInexistente() throws Exception{
		String json = "{\"titulo\": \"Um titulo com mais de 10\","
				+ "\"dataLancamento\": \"21/10/2021\","
				+ "\"numeroPaginas\": 100,"
				+ "\"idAutor\": 20}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isNotFound()); //talvez devesse ser um bad request 400
	}
	
	@Test
	void deveriaCadastrarLivroComAutorExistente() throws Exception{
		String jsonAutor ="{\"nome\": \"Um Autor Teste\","
				+ "\"email\": \"umemailteste@email.com\","
				+ "\"dataNascimento\": \"21/10/2021\","
				+ "\"miniCurriculo\": \"Algum currículo só para funcionar teste\"}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
										.post("/autores")
										.contentType(MediaType.APPLICATION_JSON)
										.content(jsonAutor))
								.andReturn();
		
		String doAutor = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				
		AutorOutputDto autor = objectMapper.readValue(doAutor, AutorOutputDto.class);
		
		String jsonLivro = "{\"titulo\": \"Um titulo com mais de 11\","
				+ "\"dataLancamento\": \"21/10/2021\","
				+ "\"numeroPaginas\": 110,"
				+ "\"idAutor\": "+autor.getId()+" }";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLivro))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().exists("Location"));
//		.andExpect(MockMvcResultMatchers.content().json(jsonLivro));
	}

}
