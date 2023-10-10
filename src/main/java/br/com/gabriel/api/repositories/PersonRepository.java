package br.com.gabriel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabriel.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}