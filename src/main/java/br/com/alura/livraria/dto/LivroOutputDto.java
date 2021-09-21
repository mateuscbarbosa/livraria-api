package br.com.alura.livraria.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroOutputDto {

	private String titulo;
	private LocalDate dataLacamento;
	private int numeroPaginas;
	private AutorOutputIdNomeDto autor;
}
