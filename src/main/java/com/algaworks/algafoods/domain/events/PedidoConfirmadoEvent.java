package com.algaworks.algafoods.domain.events;

import com.algaworks.algafoods.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {//Coloca o nome da classe no passado
	
	//Propriedades para guardar informações sobre o evento
	//Se o pedido foi confirmado, do que precisamos ?
	
	private Pedido pedido;

}
