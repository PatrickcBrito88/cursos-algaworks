package com.algaworks.algafoods.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.FormaPagamentoController;
import com.algaworks.algafoods.api.model.FormaPagamentoModel;
import com.algaworks.algafoods.domain.model.FormaPagamento;

@Component
public class FormaPagamentoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel>{

	public FormaPagamentoAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
		
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algalinks;
	
	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel =
				createModelWithId(formaPagamento.getId(), formaPagamento);
		modelMapper.map(formaPagamento, formaPagamentoModel);
		
		formaPagamentoModel.add(algalinks.linkToFormaPagamentos("formasPagamento"));
		
		return formaPagamentoModel;
		
	}
	
	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		return super.toCollectionModel(entities)
				.add(algalinks.linkToFormaPagamento());
	}
	
//	public List<FormaPagamentoModel> toCollectModel (Collection<FormaPagamento> formasPagamento){
//		return formasPagamento.stream()
//				.map(formaPagamento -> toModel(formaPagamento))
//				.collect(Collectors.toList());
//	}
}
