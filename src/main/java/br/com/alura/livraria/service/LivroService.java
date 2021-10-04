package br.com.alura.livraria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.dto.LivroOutputDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;

@Service
public class LivroService {

	private List<Livro> livros = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private AutorService autorService;
	
	public List<LivroOutputDto> listar() {
		return livros.stream().map(l -> modelMapper.map(l, LivroOutputDto.class)).collect(Collectors.toList());
	}

	public void cadastrar(LivroFormDto livroFormDto) {
		Autor autor = autorService.buscaPorId(livroFormDto.getIdAutor());
		Livro livro = modelMapper.map(livroFormDto, Livro.class);
		livro.setAutor(autor);
		livros.add(livro);
	}
	
}
