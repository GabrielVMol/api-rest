package br.com.gabriel.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	// http://localhost:8080/v3/api-docs
	// http://localhost:8080/swagger-ui/index.html
	
	
	@Bean
	OpenAPI customOpenAPI() { // Customizando a documentação do Swagger
		return new OpenAPI()
				.info(new Info() 
				.title("API with JAVA and SPRING BOOT")
				.version("v1")
				.description("Api RESTful ultilizando Java 19 e Spring Boot 3")
				.termsOfService("https://github.com/GabrielVMol")
				.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
	}
}