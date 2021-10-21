package br.com.alura.livraria.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class RelatorioLivroPorAutorOutputDto {

	private String nome;
	private Long quantidadeLivros;
	private BigDecimal percentual;
	
	public RelatorioLivroPorAutorOutputDto(String nome, Long quantidadeLivros, Double percentual) {
		this.nome = nome;
		this.quantidadeLivros = quantidadeLivros;
		this.percentual = new BigDecimal(percentual).multiply(new BigDecimal("100")).setScale(2);
	}
	
	
}
