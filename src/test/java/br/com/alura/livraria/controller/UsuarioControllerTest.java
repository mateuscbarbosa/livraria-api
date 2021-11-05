package br.com.alura.livraria.controller;

import javax.transaction.Transactional;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.livraria.dto.UsuarioAtualizacaoFormDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.infra.security.TokenService;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("teste")
@Transactional
class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private String token;
	
	@BeforeEach
	public void gerarToken() {
		Usuario logado = new Usuario("Teste de Teste", "testedeteste", "umasenhateste");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		
		usuarioRepository.save(logado);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
		this.token = tokenService.gerarToken(authentication);
	}
	
	@Test
	void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization","Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void naoDeveriaCadastrarComLoginJaExistente() throws Exception {
		UsuarioFormDto novoUsuario = new UsuarioFormDto("Teste de Login", "testedeteste", 2l);
		
		String json = objectMapper.writeValueAsString(novoUsuario);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveriaCadastrarComDadosCompletos() throws Exception{
		UsuarioFormDto novoUsuario = new UsuarioFormDto("Teste de Teste", "teste", 1l);
		
		String json = objectMapper.writeValueAsString(novoUsuario);
		String jsonEsperado = "{\"nome\": \"Teste de Teste\", \"login\": \"teste\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.header().exists("Location"))
			.andExpect(MockMvcResultMatchers.content().json(jsonEsperado));
	}
	
	@Test
	void naoDeveriaAtualizarUsuarioInexistente() throws Exception {
		UsuarioAtualizacaoFormDto usuarioForm = new UsuarioAtualizacaoFormDto(99999999l, "123456");
		
		String json = objectMapper.writeValueAsString(usuarioForm);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer" + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveriaAtualizarUsuarioSelecionado() throws Exception{
		Usuario novoUsuario = new Usuario("Usuario Atualizar", "usrat", "123456");
		Perfil admin = perfilRepository.findById(1l).get();
		novoUsuario.adicionarPerfil(admin);
		usuarioRepository.save(novoUsuario);
		
		Usuario usuario = usuarioRepository.findByLogin(novoUsuario.getLogin()).get();
		
		UsuarioAtualizacaoFormDto usuarioForm = new UsuarioAtualizacaoFormDto("Usuario Atualizado", 
																				usuario.getLogin(),
																				1l,
																				usuario.getId(),
																				"654321");
		String jsonAtualizar = objectMapper.writeValueAsString(usuarioForm);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonAtualizar)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void naoDeveriaRemoverUsuarioComIdInexistente() throws Exception{
		String json = "99999";
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/usuarios/"+json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void deveriaRemoverUsuario() throws Exception{
		Usuario novoUsuario = new Usuario("Usuario Atualizar", "usrat", "123456");
		Perfil admin = perfilRepository.findById(1l).get();
		novoUsuario.adicionarPerfil(admin);
		usuarioRepository.save(novoUsuario);
		
		Usuario usuario = usuarioRepository.findByLogin(novoUsuario.getLogin()).get();
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/usuarios/"+usuario.getId())
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
