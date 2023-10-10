package br.com.gabriel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabriel.api.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {	
}