package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.CidadeInput;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.model.Estado;

@Component
public class CidadeModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toObjectModel (CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToObjectModel (CidadeInput cidadeInput, Cidade cidade) {
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
}
