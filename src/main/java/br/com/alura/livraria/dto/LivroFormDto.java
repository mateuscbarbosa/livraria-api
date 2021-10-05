package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonAlias("data_lancamento")
	private LocalDate dataLancamento;
	
	@NotNull
	@Min(100)
	@JsonAlias("numero_paginas")
	private Integer numeroPaginas;
	
	@NotNull
	@JsonAlias("autor_id")
	private Long idAutor;
}
