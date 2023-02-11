package com.algaworks.algafoods.domain.events;

import com.algaworks.algafoods.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {
	
	private Pedido pedido;

}
