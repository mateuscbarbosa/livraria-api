package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.AutorOutputDto;
import br.com.alura.livraria.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

	@Mock
	private AutorRepository autorRespository;
	
	@InjectMocks
	private AutorService service;
	
	@Test
	void deveriaCadastrarUmAutor() {
		AutorFormDto formDto = new AutorFormDto(
				"Um Autor",
				"umautor@email.com",
				LocalDate.now(),
				"Algum currículo");
		
		AutorOutputDto dto = service.cadastrar(formDto);
		
		Mockito.verify(autorRespository).save(Mockito.any());
		
		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataNascimento(), dto.getDataNascimento());
		assertEquals(formDto.getMiniCurriculo(), dto.getMiniCurriculo());
	}
	
	@Test
	void naoDeveriaCadastrarUmAutorComEmailIgual() {
		
	}

}
