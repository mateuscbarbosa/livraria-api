package br.com.alura.livraria.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.dto.LivroOutputDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private AutorService autorService;
	
	public Page<LivroOutputDto> listar(Pageable paginacao) {
		Page <Livro> livros = livroRepository.findAll(paginacao);
		return livros.map(l -> modelMapper.map(l, LivroOutputDto.class));
	}

	@Transactional
	public LivroOutputDto cadastrar(LivroFormDto livroFormDto) {
		Autor autor = autorService.buscaPorId(livroFormDto.getIdAutor());
		Livro livro = modelMapper.map(livroFormDto, Livro.class);
		livro.setAutor(autor);
		livro.setId(null);
		
		livroRepository.save(livro);
		return  modelMapper.map(livro, LivroOutputDto.class);
	}
	
}
