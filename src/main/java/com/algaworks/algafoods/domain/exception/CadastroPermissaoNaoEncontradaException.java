package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class CadastroPermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public CadastroPermissaoNaoEncontradaException (String mensagem) {
		super (mensagem);
	}
	
	public CadastroPermissaoNaoEncontradaException (Long id) {
		this(String.format("Não existe cadastro de código %d ", id));
	}

}
