package br.com.gabriel.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{ // Resposta de exceção para quando Objeto é nulo
	
	private static final long serialVersionUID = 1L;
	
	public RequiredObjectIsNullException() { 
		super("Não é permitido inserir um objeto nulo!");
	}	
	
	public RequiredObjectIsNullException(String ex) {
		super(ex);
	}
}