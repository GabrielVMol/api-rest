package br.com.gabriel.api.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.gabriel.api.controllers.PersonController;
import br.com.gabriel.api.data.vo.v1.PersonVO;
import br.com.gabriel.api.exceptions.RequiredObjectIsNullException;
import br.com.gabriel.api.exceptions.ResourceNotFoundException;
import br.com.gabriel.api.mapper.DozerMapper;
import br.com.gabriel.api.model.Person;
import br.com.gabriel.api.repositories.PersonRepository;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	PersonRepository repository;

	public List<PersonVO> findAll() { // Find All: retorna uma lista de pessoas

		logger.info("Encontrada todas as Pessoas!");

		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO findById(Long id) { // Find by ID: retorna uma pessoa
		
		logger.info("Pessoa Encontrada!");
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado para este ID!"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonVO create(PersonVO person) { // Create: retorna a pessoa adicionada

		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Pessoa Adcionada!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO update(PersonVO person) { // Update: retorna a pessoa atualizada

		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Pessoa Atualizada!");
		
		var entity = repository.findById(person.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado para este ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) { // Delete: Não tem retorno
		
		logger.info("Pessoa Excluida!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado para este ID!"));
		repository.delete(entity);
	}
}
