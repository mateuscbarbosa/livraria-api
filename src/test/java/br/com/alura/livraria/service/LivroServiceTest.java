package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.livraria.dto.AutorOutputDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.dto.LivroOutputDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;
	
	@Mock
	private AutorService autorService;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private LivroService livroService;
	
	
	@Test
	void deveriaCadastrarUmLivroComUsuarioExistente() {
		Autor autor = new Autor(1l,"Nome","email@email",LocalDate.now(),"Curriculo");
		LivroFormDto formDto = new LivroFormDto(
				"Algum Titulo",
				LocalDate.now(),
				100,
				1l);
		
		
		when(autorService.buscaPorId(1l)).thenReturn(autor);
		
		Livro livro = new Livro(formDto.getTitulo(),formDto.getDataLancamento(),formDto.getNumeroPaginas(),autor);
		when(modelMapper.map(formDto, Livro.class)).thenReturn(livro);
		
		AutorOutputDto autorDto = modelMapper.map(autor, AutorOutputDto.class);
		
		when(modelMapper.map(livro, LivroOutputDto.class)).thenReturn(new LivroOutputDto(
																		null,
																		livro.getTitulo(),
																		livro.getDataLancamento(),
																		livro.getNumeroPaginas(),
																		autorDto));
		LivroOutputDto dto = livroService.cadastrar(formDto);
				
		Mockito.verify(livroRepository).save(Mockito.any());
		
		assertEquals(formDto.getTitulo(), dto.getTitulo());
		assertEquals(formDto.getDataLancamento(), dto.getDataLancamento());
		assertEquals(formDto.getNumeroPaginas(), dto.getNumeroPaginas());
	}
	
	@Test
	void naoDeveriaCadastrarUmLivroComUsuarioInexistente() {
		LivroFormDto formDto = new LivroFormDto(
				"Algum Titulo",
				LocalDate.now(),
				100,
				1l);
		when(autorService.buscaPorId(Mockito.anyLong())).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> livroService.cadastrar(formDto));
	}

}
