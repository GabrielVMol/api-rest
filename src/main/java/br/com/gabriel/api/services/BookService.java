package br.com.gabriel.api.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabriel.api.controllers.BookController;
import br.com.gabriel.api.data.vo.v1.BookVO;
import br.com.gabriel.api.exceptions.RequiredObjectIsNullException;
import br.com.gabriel.api.exceptions.ResourceNotFoundException;
import br.com.gabriel.api.mapper.DozerMapper;
import br.com.gabriel.api.model.Book;
import br.com.gabriel.api.repositories.BookRepository;

@Service
public class BookService {
	
	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	BookRepository repository;

	public List<BookVO> findAll() { // Find All: retorna uma lista de book

		logger.info("Encontrado todos os Livros!");

		var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		books
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return books;
	}

	public BookVO findById(Long id) { // Find by ID: retorna um book
		
		logger.info("Livro Encontrado!");
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado para este ID!"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public BookVO create(BookVO book) { // Create: retorna o book adicionado

		if (book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Livro Adcionado!");
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO book) { // Update: retorna o livro atualizado

		if (book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Livro Atualizado!");
		
		var entity = repository.findById(book.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado para este ID!"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) { // Delete: Não tem retorno
		
		logger.info("Livro Excluido");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado para este ID!"));
		repository.delete(entity);
	}
}
