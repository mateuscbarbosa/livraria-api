package br.com.alura.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.livraria.modelo.Usuario;

public interface UsuarioRespository extends JpaRepository<Usuario, Long>{

}
