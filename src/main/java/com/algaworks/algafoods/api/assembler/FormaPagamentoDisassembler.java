package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.FormaPagamentoInput;
import com.algaworks.algafoods.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toObjectModel (FormaPagamentoInput formaPagamentoInput) {
		return modelMapper
				.map(formaPagamentoInput, FormaPagamento.class);
	}
	
	public void copyToObject (FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInput, formaPagamento);
	}
}
