package br.com.alura.livraria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.livraria.modelo.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

	boolean existsByEmail(String email);
	
	Optional<Autor> findByEmail(String email);

}
