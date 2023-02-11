package com.algaworks.algafoods.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel>{
	
	@ApiModelProperty(value="Id da Cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(value="Nome da Cidade", example = "Araruama")
	private String nome;
	
	@ApiModelProperty(value="Nome do Estado", example = "Rio de Janeiro")
	private String estado;//Dessa forma o ModelMapper pega o id e o nome do Estado (Entao precisei configurar no ModelMapper)
}
