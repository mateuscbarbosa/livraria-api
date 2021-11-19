package br.com.alura.livraria.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.livraria.dto.RelatorioLivroPorAutorOutputDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("teste")
class LivroRepositoryTest {

	@Autowired
	private LivroRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	void deveriaRetornarLivrosPorAutor() {
		Autor a1 = new Autor("Primeiro Autor", "primeiro@email.com", LocalDate.now(),"Algum currículo para teste");
		em.persist(a1);
		Autor a2 = new Autor("Segundo Autor", "segundo@email.com", LocalDate.now(),"Algum currículo para teste");
		em.persist(a2);
		Autor a3 = new Autor("Terceiro Autor", "terceiro@email.com", LocalDate.now(),"Algum currículo para teste");
		em.persist(a3);
		
		Livro l1 = new Livro("Um título do livro1", LocalDate.now(), 101, a1);
		em.persist(l1);
		Livro l2 = new Livro("Um título do livro2", LocalDate.now(), 101, a1);
		em.persist(l2);
		Livro l3 = new Livro("Um título do livro3", LocalDate.now(), 101, a2);
		em.persist(l3);
		Livro l4 = new Livro("Um título do livro4", LocalDate.now(), 101, a3);
		em.persist(l4);
		
		List <RelatorioLivroPorAutorOutputDto> relatorio = repository.relatorioLivrosPorAutor();
		
		Assertions
		.assertThat(relatorio)
		.hasSize(3)
		.extracting(RelatorioLivroPorAutorOutputDto::getNome, RelatorioLivroPorAutorOutputDto::getQuantidadeLivros, RelatorioLivroPorAutorOutputDto::getPercentual)
		.containsExactlyInAnyOrder(
				Assertions.tuple("Primeiro Autor", 2l, new BigDecimal("50.00")),
				Assertions.tuple("Segundo Autor", 1l, new BigDecimal("25.00")),
				Assertions.tuple("Terceiro Autor", 1l, new BigDecimal("25.00"))
				);
	}

}
