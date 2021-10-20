package br.com.alura.livraria.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.livraria.dto.LivroAtualizacaoFormDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.dto.LivroOutputDto;
import br.com.alura.livraria.service.LivroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/livros")
@Api(tags = "Livros")
public class LivroController {

	@Autowired
	private LivroService service;
	
	@GetMapping
	@ApiOperation("Lista de Livros")
	public Page<LivroOutputDto> listar(Pageable paginacao){
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastro de novo Livro")
	public ResponseEntity<LivroOutputDto> cadastrar(@RequestBody @Valid LivroFormDto livroFormDto, UriComponentsBuilder uriBuilder) {
		LivroOutputDto livroOutputDto = service.cadastrar(livroFormDto);
		
		URI uri = uriBuilder.path("/livros/{id}").buildAndExpand(livroOutputDto.getId()).toUri();
		return ResponseEntity.created(uri).body(livroOutputDto);
	}
	
	@PutMapping
	@ApiOperation("Atualizar Livro selecionado")
	public ResponseEntity<LivroOutputDto> atualizar(@RequestBody @Valid LivroAtualizacaoFormDto livroFormDto){
		LivroOutputDto livroOutputDto = service.atualizar(livroFormDto);
		
		return ResponseEntity.ok(livroOutputDto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Remover Livro selecionado")
	public ResponseEntity<LivroOutputDto> remover(@PathVariable @NotNull Long id){
		service.remover(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Livro selecionado detalhado")
	public ResponseEntity<LivroOutputDto> detalhar(@PathVariable @NotNull Long id){
		LivroOutputDto livroOutputDto = service.detalhar(id);
		
		return ResponseEntity.ok(livroOutputDto);
	}
}
