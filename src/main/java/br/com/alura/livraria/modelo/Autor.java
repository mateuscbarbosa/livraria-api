package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Autor {

	private Long idAutor;
	private String nome;
	private String email;
	private LocalDate dataNascimento;
	private String miniCurriculo;
	
}
