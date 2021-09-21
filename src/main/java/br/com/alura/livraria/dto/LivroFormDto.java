package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import br.com.alura.livraria.modelo.Autor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroFormDto {

	@NotBlank
	@Length(min=10)
	private String titulo;
	
	@NotNull
	@PastOrPresent
	private LocalDate dataLacamento;
	
	@NotNull
	@Min(100)
	private int numeroPaginas;
	
//	@NotNull
//	private Autor autor;
}
