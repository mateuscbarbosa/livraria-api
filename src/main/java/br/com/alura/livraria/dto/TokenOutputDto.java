package br.com.alura.livraria.dto;

import lombok.Getter;

@Getter
public class TokenOutputDto {

	private String token;
	
	public TokenOutputDto(String token) {
		this.token=token;
	}
}
