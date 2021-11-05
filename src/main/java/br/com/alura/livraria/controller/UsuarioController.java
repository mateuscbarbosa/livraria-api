package br.com.alura.livraria.controller;

import java.net.URI;

import javax.validation.Valid;

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

import com.sun.istack.NotNull;

import br.com.alura.livraria.dto.UsuarioAtualizacaoFormDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.dto.UsuarioOutputDetalhadoDto;
import br.com.alura.livraria.dto.UsuarioOutputDto;
import br.com.alura.livraria.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")
@Api(tags = "Usuário")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping
	@ApiOperation("Listar Usuários")
	public Page<UsuarioOutputDto> listar(Pageable paginacao){
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar Usuário")
	public ResponseEntity<UsuarioOutputDto> cadastrar(@RequestBody @Valid UsuarioFormDto usuarioForm, UriComponentsBuilder uriBuilder){
		UsuarioOutputDto dto = service.cadastrar(usuarioForm);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping
	@ApiOperation("Atualizar Usuário selecionado")
	public ResponseEntity<UsuarioOutputDto> atualizar(@RequestBody @Valid UsuarioAtualizacaoFormDto usuarioForm){
		UsuarioOutputDto dto = service.atualizar(usuarioForm);
		
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Remover Usuário selecionado")
	public ResponseEntity<UsuarioOutputDto> remover(@PathVariable @NotNull Long id){
		service.remover(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Usuário detalhado")
	public ResponseEntity<UsuarioOutputDetalhadoDto> detalhar(@PathVariable @NotNull Long id){
		UsuarioOutputDetalhadoDto dto = service.detalhar(id);
		
		return ResponseEntity.ok(dto);
	}
}
