package br.com.alura.livraria.service;

import java.util.Random;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.UsuarioAtualizacaoFormDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.dto.UsuarioOutputDto;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRespository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRespository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<UsuarioOutputDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios.map(u -> modelMapper.map(u, UsuarioOutputDto.class));
	}

	@Transactional
	public UsuarioOutputDto cadastrar(UsuarioFormDto usuarioForm) {
		Usuario usuario = modelMapper.map(usuarioForm, Usuario.class);
		
		Perfil perfil = perfilRepository.getById(usuarioForm.getPerfilId());
		usuario.adicionarPerfil(perfil);
		
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(senha);
		
		usuarioRepository.save(usuario);
				
		return modelMapper.map(usuario, UsuarioOutputDto.class);
	}

	@Transactional
	public UsuarioOutputDto atualizar(UsuarioAtualizacaoFormDto usuarioForm) {
		Usuario usuario = usuarioRepository.getById(usuarioForm.getId());
		
		usuario.atualizarInformacoes(usuarioForm.getNome(), usuarioForm.getLogin(), usuarioForm.getSenha());
		
		return modelMapper.map(usuario, UsuarioOutputDto.class);
	}

	@Transactional
	public void remover(Long id) {
		usuarioRepository.deleteById(id);
	}

	public UsuarioOutputDto detalhar(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
											.orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(usuario, UsuarioOutputDto.class);
	}

}
