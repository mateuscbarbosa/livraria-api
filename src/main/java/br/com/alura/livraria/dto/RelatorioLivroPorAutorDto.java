package br.com.alura.livraria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RelatorioLivroPorAutorDto {

	private String nome;
	private Long quantidadeLivros;
	private Double percentual;
}
