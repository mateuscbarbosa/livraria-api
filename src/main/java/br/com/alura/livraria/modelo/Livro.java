package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Entity
@Table(name = "livros")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private LocalDate dataLancamento;
	private Integer numeroPaginas;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;

	public Livro(String titulo, LocalDate dataLancamento, Integer numeroPaginas, Autor autor) {
		super();
		this.titulo = titulo;
		this.dataLancamento = dataLancamento;
		this.numeroPaginas = numeroPaginas;
		this.autor = autor;
	}
	
	public void atualizar(Autor autor, String titulo, Integer numeroPaginas, LocalDate dataLancamento) {
		this.autor = autor;
		this.titulo = titulo;
		this.numeroPaginas = numeroPaginas;
		this.dataLancamento = dataLancamento;
		
	}
}