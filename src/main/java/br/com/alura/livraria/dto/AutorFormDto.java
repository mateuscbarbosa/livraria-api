package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorFormDto {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
	@PastOrPresent
	@NotNull
	private LocalDate dataNascimento;
	
	@NotBlank
	private String miniCurriculo;
}
