package br.com.alura.livraria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.livraria.dto.RelatorioLivroPorAutorOutputDto;
import br.com.alura.livraria.service.RelatoriosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/relatorios")
@Api(tags = "Relatórios")
public class RelatoriosController {

	@Autowired
	private RelatoriosService service;
	
	@GetMapping("/livros")
	@ApiOperation("Relátio de Livros por Autor")
	public List<RelatorioLivroPorAutorOutputDto> relatorioLivroPorAutor(){
		return service.relatorioLivrosPorAutor();
	}
}
