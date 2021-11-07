package br.com.alura.livraria.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.alura.livraria.dto.LivroAtualizacaoFormDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.infra.security.TokenService;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("teste")
@Transactional
class LivroControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private String token;
	
	@BeforeEach
	public void gerarToken() {
		Usuario logado = new Usuario("Teste Livro", "testel","123456");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		
		usuarioRepository.save(logado);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
		this.token = "Bearer " + tokenService.gerarToken(authentication);
	}
	
	private LivroFormDto gerarLivro(Long idAutor) {
		LivroFormDto livroForm = new LivroFormDto("Um titulo com mais de 10", LocalDate.now(), 100, idAutor);
		
		return livroForm;
	}
	
	private Autor cadastrarAutor() {
		Autor novoAutor = new Autor("Um Autor Teste", "autor@email.com", LocalDate.now(), "Alguma coisa Escrita");
		autorRepository.save(novoAutor);
		
		Autor cadastrado = autorRepository.findByEmail(novoAutor.getEmail()).get();
		
		return cadastrado;
	}
	
	@Test
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception{
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveriaCadastrarLivroComAutorInexistente() throws Exception{
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String json = objectMapper.writeValueAsString(gerarLivro(9999l));
		
		mvc.perform(MockMvcRequestBuilders
				.post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void deveriaCadastrarLivroComAutorExistente() throws Exception{
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		String jsonLivro = objectMapper.writeValueAsString(gerarLivro(cadastrarAutor().getId()));
		
		mvc.perform(MockMvcRequestBuilders
				.post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLivro)
				.header("Authorization", token))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().exists("Location"));
	}
	
	@Test
	void naoDeveriaAtualizarLivroComAutorInexistente() throws Exception{
		Livro novoLivro = new Livro("Novo Livro", LocalDate.now(), 100, cadastrarAutor());
		livroRepository.save(novoLivro);
		Livro cadastrado = livroRepository.findByTitulo(novoLivro.getTitulo()).get();
		
		LivroAtualizacaoFormDto livroForm= new LivroAtualizacaoFormDto("Novo Titulo",
				LocalDate.parse("2000-10-10"),
				200,
				999l,
				cadastrado.getId());
		
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String json = objectMapper.writeValueAsString(livroForm);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	void naoDeveriaAtualizarLivroComIdInexistente() throws Exception{
		LivroAtualizacaoFormDto livroForm= new LivroAtualizacaoFormDto("Novo Titulo",
				LocalDate.parse("2000-10-10"),
				200,
				cadastrarAutor().getId(),
				9999l);
		
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String json = objectMapper.writeValueAsString(livroForm);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	void deveriaAtualizarLivro() throws Exception{
		Livro novoLivro = new Livro("Novo Livro", LocalDate.now(), 100, cadastrarAutor());
		livroRepository.save(novoLivro);
		Livro cadastrado = livroRepository.findByTitulo(novoLivro.getTitulo()).get();
		
		LivroAtualizacaoFormDto livroForm= new LivroAtualizacaoFormDto("Novo Titulo",
																		LocalDate.parse("2000-10-10"),
																		200,
																		cadastrado.getAutor().getId(),
																		cadastrado.getId());
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String json = objectMapper.writeValueAsString(livroForm);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void deveriaRemoverLivro() throws Exception{
		Livro livro = new Livro("Novo Livro", LocalDate.now(), 100, cadastrarAutor());
		livroRepository.save(livro);
		Livro cadastrado = livroRepository.findByTitulo(livro.getTitulo()).get();
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/livros/"+cadastrado.getId())
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
