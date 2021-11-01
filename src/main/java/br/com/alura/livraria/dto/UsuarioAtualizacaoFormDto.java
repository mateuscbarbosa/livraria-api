package br.com.alura.livraria.dto;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizacaoFormDto extends UsuarioFormDto{
	
	@NotNull
	private Long id;
	
	@NotBlank
	private String senha;
}
