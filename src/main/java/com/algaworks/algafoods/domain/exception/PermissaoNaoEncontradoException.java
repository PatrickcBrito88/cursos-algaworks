package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class PermissaoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public PermissaoNaoEncontradoException (String mensagem) {
		super (mensagem);
	}
	
	public PermissaoNaoEncontradoException (Long pedidoId) {
		this(String.format("Não existe pedido de código %d",pedidoId));
	}

}
