package com.algaworks.algafoods.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.logging.formatter.CollectionOfObjectsToStringFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.RestauranteProdutoController;
import com.algaworks.algafoods.api.model.ProdutoModel;
import com.algaworks.algafoods.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel>{

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	
	public ProdutoModelAssembler() {
		super(RestauranteProdutoController.class, ProdutoModel.class);
		//O link de roteamento do createModelWithId vem do RestauranteProdutoController
	}
	
	@Override
	public ProdutoModel toModel (Produto produto) {
		ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
		modelMapper.map(produto, produtoModel);
		
		produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
		produtoModel.add(algaLinks.linkToFotos(produto.getId(), produto.getRestaurante().getId(), "foto"));
		
		return produtoModel;
	}
	
	@Override
	public CollectionModel<ProdutoModel> toCollectionModel(Iterable<? extends Produto> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToPedidos(IanaLinkRelations.SELF.value()));
	}

}
