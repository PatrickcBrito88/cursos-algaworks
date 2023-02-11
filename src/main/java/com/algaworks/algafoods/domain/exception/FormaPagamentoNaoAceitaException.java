package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class FormaPagamentoNaoAceitaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoNaoAceitaException (String mensagem) {
		super (mensagem);
	}
	
	public FormaPagamentoNaoAceitaException (String formaPagamento, Long restauranteId) {
		this(String.format("A forma de pagamento %s não é aceita pelo restaurante de código %d.",
				formaPagamento, restauranteId));
	}
	
}
