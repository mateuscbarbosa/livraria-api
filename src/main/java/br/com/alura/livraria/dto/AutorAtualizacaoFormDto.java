package br.com.alura.livraria.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorAtualizacaoFormDto extends AutorFormDto{
	
	@NotNull
	private Long id;
}
