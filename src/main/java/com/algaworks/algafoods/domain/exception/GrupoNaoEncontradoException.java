package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException (String mensagem) {
		super (mensagem);
	}
	
	public GrupoNaoEncontradoException (Long grupoId) {
		this(String.format("Não existe grupo de código %d ", grupoId));
	}

}
