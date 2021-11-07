package br.com.alura.livraria.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.alura.livraria.dto.AutorAtualizacaoFormDto;
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
class AutorControllerTest {

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
	private void gerarToken() {
		Usuario logado = new Usuario("Teste Autor", "testea", "123456");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		
		usuarioRepository.save(logado);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
		this.token = "Bearer " + tokenService.gerarToken(authentication);
	}
	
	private Autor gerarAutor(String email) {
		Autor autor = new Autor("Um Autor",email,LocalDate.parse("10/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")),"Curriculo Aleatório");
		
		return autor;
	}
	
	@Test
	void naoDeveriaCadastrarAutorComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveriaCadastrarAutorComEmailsIguais() throws Exception {
		autorRepository.save(gerarAutor("umemail@email.com"));
		
		Autor autor2 = gerarAutor("umemail@email.com");
		
		objectMapper.registerModule(new JavaTimeModule());
		String json = objectMapper.writeValueAsString(autor2);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveriaCastararAutorComDadosCompletos() throws Exception{
		objectMapper.registerModule(new JavaTimeModule());
		String json = objectMapper.writeValueAsString(gerarAutor("outro@email.com"));
		
		String jsonResultado = "{ \"dataNascimento\": \"10/10/2000\","
								+ " \"email\": \"outro@email.com\","
								+ " \"miniCurriculo\": \"Curriculo Aleatório\","
								+ " \"nome\": \"Um Autor\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.header().exists("Location"))
			.andExpect(MockMvcResultMatchers.content().json(jsonResultado));
	}
	
	@Test
	void deveriaAtualizarAutor() throws Exception{
		Autor novoAutor = gerarAutor("email@email.com");
		autorRepository.save(novoAutor);
		
		Autor cadastrado = autorRepository.findByEmail(novoAutor.getEmail()).get();
		
		AutorAtualizacaoFormDto autorForm = new AutorAtualizacaoFormDto("Outro Nome",
																		cadastrado.getEmail(),
																		cadastrado.getDataNascimento(),
																		"Estudou em algum lugar",
																		cadastrado.getId());
		objectMapper.registerModule(new JavaTimeModule());
		String json = objectMapper.writeValueAsString(autorForm);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(json));
	}
	
	@Test
	void naoDeveriaRemoverAutorComLivroRegistrado() throws Exception{
		Autor autor = gerarAutor("outro@email.com");
		autorRepository.save(autor);
		Autor autorCadastrado = autorRepository.findByEmail(autor.getEmail()).get();
		
		Livro livro = new Livro("Titulo Teste", LocalDate.now(), 100, autorCadastrado);
		livroRepository.save(livro);
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/autores/"+autorCadastrado.getId())
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveriaRemoverAutorSemLivroRegistrado() throws Exception{
		Autor autor = gerarAutor("outro@email.com");
		autorRepository.save(autor);
		Autor autorCadastrado = autorRepository.findByEmail(autor.getEmail()).get();
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/autores/"+autorCadastrado.getId())
				.header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
