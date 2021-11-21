package br.com.alura.livraria.service;

import java.util.Random;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.UsuarioAtualizacaoFormDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.dto.UsuarioOutputDetalhadoDto;
import br.com.alura.livraria.dto.UsuarioOutputDto;
import br.com.alura.livraria.infra.EnviadorDeEmail;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EnviadorDeEmail enviadorDeEmail;
	
	public Page<UsuarioOutputDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios.map(u -> modelMapper.map(u, UsuarioOutputDto.class));
	}

	@Transactional
	public UsuarioOutputDto cadastrar(UsuarioFormDto usuarioForm) {
		boolean temLoginCadastrado = usuarioRepository.existsByLogin(usuarioForm.getLogin());
		if(temLoginCadastrado) {
			throw new DataIntegrityViolationException("Login indisponível!");
		}
		Usuario usuario = modelMapper.map(usuarioForm, Usuario.class);
		
		Perfil perfil = perfilRepository.getById(usuarioForm.getPerfilId());
		usuario.adicionarPerfil(perfil);
		
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		usuario.setId(null);
		
		usuarioRepository.save(usuario);
		
		String destinataro = usuario.getEmail();
		String assunto = "Livraria - Bem vindo(a)!";
		String mensagem = String.format("Olá %s\n\n"
				+ "Seguem seus dados de acesso ao sistema.\n\n"
				+ "Login:%s\n"
				+ "Senha:%s", usuario.getNome(),usuario.getLogin(),senha);
		enviadorDeEmail.enviarEmail(destinataro, assunto, mensagem);
				
		return modelMapper.map(usuario, UsuarioOutputDto.class);
	}

	@Transactional
	public UsuarioOutputDto atualizar(UsuarioAtualizacaoFormDto usuarioForm) {
		Usuario usuario = usuarioRepository.getById(usuarioForm.getId());
		
		usuario.atualizarInformacoes(usuarioForm.getNome(),
									 usuarioForm.getLogin(),
									 bCryptPasswordEncoder.encode(usuarioForm.getSenha()),
									 usuarioForm.getEmail());
		
		return modelMapper.map(usuario, UsuarioOutputDto.class);
	}

	@Transactional
	public void remover(Long id) {
		usuarioRepository.deleteById(id);
	}

	public UsuarioOutputDetalhadoDto detalhar(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
											.orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(usuario, UsuarioOutputDetalhadoDto.class);
	}

}
