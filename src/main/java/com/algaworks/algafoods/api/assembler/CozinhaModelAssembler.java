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
import com.algaworks.algafoods.api.controller.CozinhaController;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends 
	RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}
		
	public CozinhaModel toModel (Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		
		cozinhaModel.add(algaLinks.linkToCozinha("cozinhas"));
		
		return cozinhaModel;
		
	}
	
	@Override
	public CollectionModel<CozinhaModel> toCollectionModel(Iterable<? extends Cozinha> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
				.add(linkTo(CozinhaController.class).withSelfRel());
	}
	
//	public List<CozinhaModel> toCollectModel (List<Cozinha> cozinhas){
//		return cozinhas.stream()
//				.map(cozinha -> toModel (cozinha))
//				.collect(Collectors.toList());	
//	}

}
