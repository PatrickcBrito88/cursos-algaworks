package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.ProdutoInput;
import com.algaworks.algafoods.domain.model.Produto;

@Component 
public class ProdutoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toObjectModel (ProdutoInput produtoInput) {
		return modelMapper
				.map(produtoInput, Produto.class);
	}
	
	public void copyToObject (ProdutoInput produtoInput, Produto produto) {
		modelMapper.map(produtoInput, produto);
	}
}
