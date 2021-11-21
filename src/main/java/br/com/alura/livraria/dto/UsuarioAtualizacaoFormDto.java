package br.com.alura.livraria.dto;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizacaoFormDto extends UsuarioFormDto{
	
	@NotNull
	private Long id;
	
	@NotBlank
	private String senha;

	public UsuarioAtualizacaoFormDto(String nome, String login, Long perfilId, Long id, String senha, String email) {
		super(nome, login, perfilId, email);
		this.id = id;
		this.senha = senha;
	}
	

}
