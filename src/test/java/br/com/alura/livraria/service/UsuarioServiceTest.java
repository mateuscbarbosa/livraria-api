package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.dto.UsuarioOutputDto;
import br.com.alura.livraria.infra.EnviadorDeEmail;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private PerfilRepository perfilRepository;
	
	@Mock
	private EnviadorDeEmail enviadorDeEmail;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	@Test
	void deveriaCadastrarUmUsuario() {
		UsuarioFormDto usuarioForm = new UsuarioFormDto("Teste", "teste", 1l,"teste@email.com");
		
		Usuario usuario = new Usuario(usuarioForm.getNome(),usuarioForm.getLogin());
		
		Mockito.when(modelMapper.map(usuarioForm, Usuario.class)).thenReturn(usuario);
		
		Mockito.when(modelMapper.map(usuario, UsuarioOutputDto.class)).thenReturn(new UsuarioOutputDto(null, usuario.getNome(), usuario.getLogin()));
		
		UsuarioOutputDto dto = usuarioService.cadastrar(usuarioForm);
		
		Mockito.verify(usuarioRepository).save(Mockito.any());
		
		assertEquals(usuarioForm.getNome(), dto.getNome());
		assertEquals(usuarioForm.getLogin(), dto.getLogin());
	}

}
