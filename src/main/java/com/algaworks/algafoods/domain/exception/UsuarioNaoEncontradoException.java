package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEncontradoException (String mensagem) {
		super (mensagem);
	}
	
	public UsuarioNaoEncontradoException (Long usuarioId) {
		this(String.format("Não existe usuário de código %d ", usuarioId));
	}

}
