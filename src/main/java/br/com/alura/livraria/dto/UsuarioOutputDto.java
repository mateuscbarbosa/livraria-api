package br.com.alura.livraria.dto;

import java.util.List;

import br.com.alura.livraria.modelo.Perfil;
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
	private List<Perfil> perfis;
}
