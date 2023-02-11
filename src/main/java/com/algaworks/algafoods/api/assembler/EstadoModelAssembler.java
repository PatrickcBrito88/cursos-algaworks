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
import com.algaworks.algafoods.api.controller.EstadoController;
import com.algaworks.algafoods.api.model.EstadoModel;
import com.algaworks.algafoods.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel>{
	
	public EstadoModelAssembler() {
		super(EstadoController.class, EstadoModel.class);
		
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public EstadoModel toModel (Estado estado) {
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
		modelMapper.map(estado, estadoModel);
		
		//Link de todos
		estadoModel.add(algaLinks.linkToEstado("estados"));
		
		 return estadoModel;
	}
	
	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToEstados());
	}
	
//	public List<EstadoModel> toCollectModel (List<Estado> estados){
//		return estados.stream()
//				.map(estado -> toModel (estado))
//				.collect(Collectors.toList());
//	}

}
