package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.PermissaoInput;
import com.algaworks.algafoods.domain.model.Permissao;

@Component
public class PermissaoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Permissao toObjectModel (PermissaoInput permissaoInput) {
		return modelMapper
				.map(permissaoInput, Permissao.class);
	}
	
	public void copyToObject (PermissaoInput permissaoInput, Permissao permissao) {
		modelMapper.map(permissaoInput, permissao);
	}
}
