package br.com.alura.livraria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioOutputDto {
	
	private Long id;
	private String nome;
	private String login;
	
	public UsuarioOutputDto(String nome, String login) {
		super();
		this.nome = nome;
		this.login = login;
	}
	
}
