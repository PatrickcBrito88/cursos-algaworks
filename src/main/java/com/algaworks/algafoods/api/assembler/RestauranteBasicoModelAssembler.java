package com.algaworks.algafoods.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.RestauranteController;
import com.algaworks.algafoods.api.model.RestauranteBasicoModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel>{

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	
	public RestauranteBasicoModelAssembler() {
		super(RestauranteController.class, RestauranteBasicoModel.class);
	}

	
	@Override
	public RestauranteBasicoModel toModel(Restaurante restaurante) {
		RestauranteBasicoModel restauranteBasicoModel =
				createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteBasicoModel);
		
		restauranteBasicoModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteBasicoModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		
		return restauranteBasicoModel;
	}
	
	@Override
	public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(RestauranteController.class).withSelfRel());
	}
	
}
