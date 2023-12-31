package br.com.gabriel.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.api.data.vo.v1.PersonVO;
import br.com.gabriel.api.services.PersonService;
import br.com.gabriel.api.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "Pessoa", description = "Endpoints para gerenciamento de Pessoas")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@CrossOrigin(origins = {"http://localhost:8080", "http://gabrielvinicius.com.br"})
	@GetMapping(
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Achar todas as Pessoas", description = "Achar todas as Pessoas por JSON, XML ou YML",
		tags = {"Pessoa"}, responses = {
				@ApiResponse(description = "Sucesso", responseCode = "200", 
						content = {@Content(mediaType = "application/json", 
						array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))}),
				@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
				@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
				@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
				@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public List<PersonVO> findAll() {
		return service.findAll();
	}
	
	@CrossOrigin(origins = {"http://localhost:8080", "http://gabrielvinicius.com.br"})
	@GetMapping(value = "/{id}",
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Achar a Pessoa", description = "Achar a Pessoa por JSON, XML ou YML",
	tags = {"Pessoa"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@CrossOrigin(origins = {"http://localhost:8080", "http://gabrielvinicius.com.br"})
	@PostMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Adcionar uma Nova Pessoa", description = "Adcionar uma Nova Pessoa por JSON, XML ou YML",
	tags = {"Pessoa"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public PersonVO create(@RequestBody PersonVO person) {
		return service.create(person);
	}
	
	@PutMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Atualizar dados da Pessoa", description = "Atualizar dados da Pessoa por JSON, XML ou YML",
	tags = {"Pessoa"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public PersonVO update(@RequestBody PersonVO person) {
		return service.update(person);
	}
	
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletar Pessoa", description = "Deletar Pessoa por JSON, XML ou YML",
	tags = {"Pessoa"}, responses = {
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}