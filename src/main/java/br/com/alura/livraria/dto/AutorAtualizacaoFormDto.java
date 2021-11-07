package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorAtualizacaoFormDto extends AutorFormDto{
	
	@NotNull
	private Long id;

	public AutorAtualizacaoFormDto(String nome, String email, LocalDate dataNascimento, String miniCurriculo,
			Long id) {
		super(nome, email, dataNascimento, miniCurriculo);
		this.id = id;
	}
	
	
	
}
