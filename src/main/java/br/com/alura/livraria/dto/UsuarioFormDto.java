package br.com.alura.livraria.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFormDto {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String login;
	
	@NotNull
	@JsonAlias("perfil_id")
	private Long perfilId;
}
