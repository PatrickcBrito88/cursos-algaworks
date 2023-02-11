package com.algaworks.algafoods.api.model;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafoods.domain.model.Permissao;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoModel {

	@ApiModelProperty(value="ID do gruo", example = "1")
	private Long id;
	
	@ApiModelProperty(value="Nome do Grupo", example = "Gerente")
	private String nome;
	
}
