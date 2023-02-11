package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.PedidoInput;
import com.algaworks.algafoods.domain.model.Pedido;

@Component
public class PedidoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toObjectModel (PedidoInput pedidoInput) {
		return modelMapper
				.map(pedidoInput, Pedido.class);
	}
	
	public void copyToObject (PedidoInput pedidoInput, Pedido pedido) {
		modelMapper.map(pedidoInput, pedido);
	}
	
}
