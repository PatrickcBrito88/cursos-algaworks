package com.algaworks.algafoods.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.RestauranteController;
import com.algaworks.algafoods.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler extends 
	RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel>{

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLink;
	
	
	
	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModel.class);
	}

	@Override
	public RestauranteApenasNomeModel toModel (Restaurante restaurante) {
		RestauranteApenasNomeModel restauranteApenasNomeModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteApenasNomeModel);
		
		//O apenas nome é uma lista por isso sem o rel. Ele assume o self rel
		restauranteApenasNomeModel.add(algaLink.linkToRestaurantes("restaurantes"));
		
		return restauranteApenasNomeModel;
	}
	
	@Override
	public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(RestauranteController.class).withSelfRel());
	}
}
