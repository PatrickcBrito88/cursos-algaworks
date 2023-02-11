package com.algaworks.algafoods.api.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoModel {

	@ApiModelProperty(value="Id da permissão", example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome da permissão", example="CONSULTAR_COZINHAS")
	private String nome;
	
	@ApiModelProperty(value="Descrição da permissão", example="Permite consultar cozinhas")
	private String descricao;
	
}
