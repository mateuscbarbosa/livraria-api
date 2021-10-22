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
import br.com.alura.livraria.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;
	
	@Mock
	private AutorService autorService;
	
	@InjectMocks
	private LivroService livroService;
	
	@Test
	void deveriaCadastrarUmLivroComUsuarioExistente() {
		ModelMapper model = new ModelMapper();
		AutorOutputDto autor = new AutorOutputDto(1l,"Nome","email@email",LocalDate.now(),"Curriculo");
		LivroFormDto formDto = new LivroFormDto(
				"Algum Titulo",
				LocalDate.now(),
				100,
				1l);
		
		when(autorService.buscaPorId(1l)).thenReturn(model.map(autor, Autor.class));
		
		LivroOutputDto dto = livroService.cadastrar(formDto);
				
		Mockito.verify(livroRepository).save(Mockito.any());
		
		assertEquals(formDto.getTitulo(), dto.getTitulo());
		assertEquals(formDto.getDataLancamento(), dto.getDataLancamento());
		assertEquals(formDto.getNumeroPaginas(), dto.getNumeroPaginas());
		assertEquals(formDto.getIdAutor(), dto.getAutor().getId());
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
