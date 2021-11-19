package br.com.alura.livraria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.livraria.dto.RelatorioLivroPorAutorOutputDto;
import br.com.alura.livraria.modelo.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

	@Query("SELECT NEW br.com.alura.livraria.dto.RelatorioLivroPorAutorOutputDto("
			+ "a.nome, "
			+ "COUNT(l.id), "
			+ "(COUNT(a.nome) / (SELECT COUNT(l2.id)*1.0 FROM Livro l2)) ) "
			+ "FROM Livro l "
			+ "JOIN l.autor a "
			+ "GROUP BY a.nome")
	List<RelatorioLivroPorAutorOutputDto> relatorioLivrosPorAutor();
	
	boolean existsByAutorId(Long id);

	Optional<Livro> findByTitulo(String titulo);
}
