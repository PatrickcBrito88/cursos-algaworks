package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public FotoProdutoNaoEncontradoException (String mensagem) {
		super (mensagem);
	}
	
	public FotoProdutoNaoEncontradoException (Long produtoId, Long restauranteId) {
		this(String.format("Não existe Foto de Produto de código %d para o "
				+ "restaurante de código %d ", produtoId, restauranteId));
	}

}
