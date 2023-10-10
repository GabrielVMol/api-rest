package br.com.gabriel.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.api.data.vo.v1.BookVO;
import br.com.gabriel.api.services.BookService;
import br.com.gabriel.api.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Livro", description = "Endpoints para gerenciamento de Livros")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping(
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Achar todas as Livros", description = "Achar todas as Livros por JSON, XML ou YML",
		tags = {"Livro"}, responses = {
				@ApiResponse(description = "Sucesso", responseCode = "200", 
						content = {@Content(mediaType = "application/json", 
						array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))}),
				@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
				@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
				@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
				@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public List<BookVO> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}",
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Achar a Livro", description = "Achar a Livro por JSON, XML ou YML",
	tags = {"Livro"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public BookVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@PostMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Adcionar uma Nova Livro", description = "Adcionar uma Nova Livro por JSON, XML ou YML",
	tags = {"Livro"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public BookVO create(@RequestBody BookVO book) {
		return service.create(book);
	}
	
	@PutMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Atualizar dados da Livro", description = "Atualizar dados da Livro por JSON, XML ou YML",
	tags = {"Livro"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public BookVO update(@RequestBody BookVO book) {
		return service.update(book);
	}
	
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletar Livro", description = "Deletar Livro por JSON, XML ou YML",
	tags = {"Livro"}, responses = {
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