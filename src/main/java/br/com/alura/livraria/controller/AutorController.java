package br.com.alura.livraria.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.AutorOutputDto;
import br.com.alura.livraria.service.AutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/autores")
@Api(tags = "Autores")
public class AutorController {

	@Autowired
	private AutorService service;
	
	@GetMapping
	@ApiOperation("Lista de Autores")
	public Page<AutorOutputDto> listar(Pageable paginacao){
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastro de novo Autor")
	public ResponseEntity<AutorOutputDto> cadastrar(@RequestBody @Valid AutorFormDto AutorFormDto, UriComponentsBuilder uriBuilder) {
		AutorOutputDto autorOutputDto = service.cadastrar(AutorFormDto);
		
		URI uri = uriBuilder.path("/autores/{id}").buildAndExpand(autorOutputDto.getId()).toUri();
		return ResponseEntity.created(uri).body(autorOutputDto);
	}
}
