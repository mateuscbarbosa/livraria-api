package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

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
public class AutorFormDto {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
	@NotNull
	@PastOrPresent
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonAlias("data_nascimento")
	private LocalDate dataNascimento;
	
	@NotBlank
	@JsonAlias("mini_curriculo")
	private String miniCurriculo;
}
