package br.com.alura.livraria.service;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.dto.AutorAtualizacaoFormDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.AutorOutputDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public Page<AutorOutputDto> listar(Pageable paginacao) {
		Page<Autor> autores = autorRepository.findAll(paginacao);
		return autores.map(a -> modelMapper.map(a, AutorOutputDto.class));
	}

	@Transactional
	public AutorOutputDto cadastrar(AutorFormDto autorFormDto) {
		boolean temEmailCadastrado = autorRepository.existsByEmail(autorFormDto.getEmail());
		if(temEmailCadastrado) {
			throw new DataIntegrityViolationException("E-mail indisponível!");
		}
		
		Autor autor = modelMapper.map(autorFormDto, Autor.class);
		
		autorRepository.save(autor);
		
		return modelMapper.map(autor, AutorOutputDto.class);
	}
	
	public Autor buscaPorId(Long id) {
		Autor autor = autorRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
		return autor;
	}

	@Transactional
	public AutorOutputDto atualizar(AutorAtualizacaoFormDto autorFormDto) {
		Autor autor = autorRepository.getById(autorFormDto.getId());
		
		autor.atualizarInformacoes(autorFormDto.getNome(), autorFormDto.getEmail(), autorFormDto.getDataNascimento(), autorFormDto.getMiniCurriculo());
		
		return modelMapper.map(autor, AutorOutputDto.class);
	}
	
	@Transactional
	public void remover(Long id) {
		boolean temLivrosCadastrados = livroRepository.existsByAutorId(id);
		if(temLivrosCadastrados) {
			throw new DataIntegrityViolationException("Autor possuí Livros registrados. Não pode ser excluído!");
		}
		autorRepository.deleteById(id);
	}

	public AutorOutputDto detalhar(Long id) {
		Autor autor = autorRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(autor, AutorOutputDto.class);
	}

}
