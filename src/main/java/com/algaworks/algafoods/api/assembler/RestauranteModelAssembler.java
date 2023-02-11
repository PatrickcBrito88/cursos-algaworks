package com.algaworks.algafoods.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.RestauranteController;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.api.model.input.RestauranteInput;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends 
	RepresentationModelAssemblerSupport<Restaurante, RestauranteModel>{

	
	//Para usar o ModelMapper tem que fazer uma configuração que esta la no pacote core
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

		
	@Override
	public RestauranteModel toModel (Restaurante restaurante) {
		
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		if (restaurante.getEndereco()!=null
				&& restaurante.getEndereco().getCidade()!=null) {
		restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		}
		
		restauranteModel.add(algaLinks.linkToRestaurantesFormasPagamento(restaurante.getId()));
		
		restauranteModel.add(algaLinks.linkToRestauranteResponsavel(restaurante.getId()));
		
		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		if (restaurante.podeAbrir()) {
			restauranteModel.add(algaLinks.linkToRestauranteAbrir(restaurante.getId(), "abrir"));
		}
		
		if (restaurante.podeFechar()) {
			restauranteModel.add(algaLinks.linkToRestauranteFechar(restaurante.getId(), "fechar"));
		}
		
		if (restaurante.podeAtivar()) {
			restauranteModel.add(algaLinks.linkToRestauranteAtivar(restaurante.getId(), "ativar"));
		}
		
		if (restaurante.podeInativar()) {
			restauranteModel.add(algaLinks.linkToRestauranteInativar(restaurante.getId(), "inativar"));
		}
		
		restauranteModel.getProdutos().forEach(produto ->{
			restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId()));
		});
		
		return restauranteModel;
		
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}
	
//	public List<RestauranteModel> toCollectModel (List<Restaurante> restaurantes){
//		return restaurantes.stream()
//				.map(restaurante ->toModel(restaurante))
//				.collect(Collectors.toList());
//	}
	
	
	
}
