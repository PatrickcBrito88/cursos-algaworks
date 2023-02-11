package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.EstadoInput;
import com.algaworks.algafoods.domain.model.Estado;

@Component
public class EstadoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toObjectModel (EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	public void copyToObject (EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}
}
