package br.com.alura.livraria.infra;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxSwaggerConfigurations {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"API Livraria",
				"Sistema para registro de Autores e Livros",
				"Termos de Uso",
				"Termos de Serviço",
				new Contact("Mateus Barbosa", "mateuscbarbosa@gmail.com","semsite.com.br"),
				"License of API", "API License URL", Collections.emptyList());
	}
}
