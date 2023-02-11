package com.algaworks.algafoods.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.openapi.controller.FluxoControlerOpenApi;
import com.algaworks.algafoods.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(path="/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoControlerOpenApi{
	
	@Autowired
	private FluxoPedidoService fluxoPedidoService;
	
	@Override
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar (@PathVariable String codigoPedido) {
		fluxoPedidoService.confirmar(codigoPedido);
		/*
		 * O método fluxopedidocontroler.confirmar estava como void, 
		 * mas voi não dá retorno e o Linkto precisa de um retorno. 
		 * A solução foi colocar ResponseEntity<Void> na assinatura de todos os métodos
		 */
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar (@PathVariable String codigoPedido) {
		fluxoPedidoService.cancelar(codigoPedido);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar (@PathVariable String codigoPedido) {
		fluxoPedidoService.entregar(codigoPedido);
		return ResponseEntity.noContent().build();
	}

}
