package br.com.alura.livraria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.AutorOutputDto;
import br.com.alura.livraria.modelo.Autor;

@Service
public class AutorService {
	
	private List<Autor> autores = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();

	public List<AutorOutputDto> listar() {
		return autores.stream().map(a -> modelMapper.map(a, AutorOutputDto.class)).collect(Collectors.toList());
	}

	public void cadastrar(AutorFormDto autorFormDto) {
		Autor autor = modelMapper.map(autorFormDto, Autor.class);
		Long ultimo = Long.valueOf(autores.size()+1);
		autor.setIdAutor(ultimo);
		System.out.println(autor.getIdAutor());
		autores.add(autor);
	}
	
	public Autor buscaPorId(Long id) {
		return autores
				.stream()
				.filter(autor -> autor.getIdAutor()==id)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Autor não encontrado."));	
	}

}
