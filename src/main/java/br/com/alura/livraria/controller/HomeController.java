package br.com.alura.livraria.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/home")
@Api(tags = "Pagina Home")
public class HomeController {

	@GetMapping
	@ApiOperation("Pagina Home do Sistema")
	public String home() {
		return "Livraria Home em construção";
	}
}
