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
public class LivroAtualizacaoFormDto extends LivroFormDto{

	@NotNull
	private Long id;

	public LivroAtualizacaoFormDto(String titulo, LocalDate dataLancamento, Integer numeroPaginas, Long idAutor,
			Long id) {
		super(titulo, dataLancamento, numeroPaginas, idAutor);
		this.id = id;
	}
	
	
}
