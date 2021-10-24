package br.com.alura.livraria.service;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.dto.AutorAtualizacaoFormDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.AutorOutputDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	private ModelMapper modelMapper = new ModelMapper();

	public Page<AutorOutputDto> listar(Pageable paginacao) {
		Page<Autor> autores = autorRepository.findAll(paginacao);
		return autores.map(a -> modelMapper.map(a, AutorOutputDto.class));
	}

	@Transactional
	public AutorOutputDto cadastrar(AutorFormDto autorFormDto) {
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
		autorRepository.deleteById(id);
	}

	public AutorOutputDto detalhar(Long id) {
		Autor autor = autorRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(autor, AutorOutputDto.class);
	}

}
