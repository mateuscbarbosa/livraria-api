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

@RestController
@RequestMapping("/autores")
public class AutorController {

	@Autowired
	private AutorService service;
	
	@GetMapping
	public Page<AutorOutputDto> listar(Pageable paginacao){
		return service.listar(paginacao);
	}
	
	@PostMapping
	public ResponseEntity<AutorOutputDto> cadastrar(@RequestBody @Valid AutorFormDto AutorFormDto, UriComponentsBuilder uriBuilder) {
		AutorOutputDto autorOutputDto = service.cadastrar(AutorFormDto);
		
		URI uri = uriBuilder.path("/autores/{id}").buildAndExpand(autorOutputDto.getId()).toUri();
		return ResponseEntity.created(uri).body(autorOutputDto);
	}
}
