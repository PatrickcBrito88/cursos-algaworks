package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.GrupoInput;
import com.algaworks.algafoods.domain.model.Grupo;

@Component
public class GrupoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toObjectModel (GrupoInput grupoInput) {
		return modelMapper
				.map(grupoInput, Grupo.class);
	}
	
	public void copyToObject (GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
}
