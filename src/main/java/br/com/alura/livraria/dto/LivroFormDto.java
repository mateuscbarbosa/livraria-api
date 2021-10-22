package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroFormDto {

	@NotBlank
	@Size(min=10)
	private String titulo;
	
	@NotNull
	@PastOrPresent
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonAlias("data_lancamento")
	private LocalDate dataLancamento;
	
	@NotNull
	@Min(value = 100, message="{livro.paginas.tamanhoMinimo}")
	@JsonAlias("numero_paginas")
	private Integer numeroPaginas;
	
	@NotNull
	@JsonAlias("autor_id")
	private Long idAutor;
}
