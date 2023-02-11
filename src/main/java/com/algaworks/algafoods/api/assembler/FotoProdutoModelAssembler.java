package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafoods.api.model.FotoProdutoModel;
import com.algaworks.algafoods.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel>{

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FotoProdutoModelAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
	}
		
	@Override
	public FotoProdutoModel toModel (FotoProduto fotoProduto) {
		FotoProdutoModel fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);
		
		fotoProdutoModel.add(algaLinks.linkToFotos(fotoProduto.getProduto().getId(), fotoProduto.getRestauranteId()));
		fotoProdutoModel.add(algaLinks.linkToFotos(fotoProduto.getProduto().getId(), fotoProduto.getRestauranteId(), "foto"));
		
		return fotoProdutoModel;
		
	}

	
	
	
}
