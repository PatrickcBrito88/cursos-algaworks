package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoException (String mensagem) {
		super (mensagem);
	}
	
	public ProdutoNaoEncontradoException (Long produtoId, Long restauranteId) {
		this(String.format("Não existe produto de código %d para o "
				+ "restaurante de código %d ", produtoId, restauranteId));
	}

}
