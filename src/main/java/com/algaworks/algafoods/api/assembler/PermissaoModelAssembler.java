package com.algaworks.algafoods.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.PermissaoModel;
import com.algaworks.algafoods.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PermissaoModel toModel (Permissao permissao) {
		return modelMapper
				.map(permissao, PermissaoModel.class);
	}
	
	public List<PermissaoModel> toCollectModel (Collection<Permissao> permissoes){
		return permissoes.stream()
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}
}
