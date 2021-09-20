package br.com.alura.livraria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
		autor.setIdAutor(new Random().nextInt(99));
		autores.add(autor);
	}

}
