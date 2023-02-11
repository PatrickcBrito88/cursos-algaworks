package com.algaworks.algafoods.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")//Agrupa todos os emmbedeb da lista Hal e muda o nome para cidades
@ApiModel (value="cidade", description = "Representa uma cidade")// Para começar a personalizar o model no OpenApi Swagger
@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel>{
//Representation Model é um conteiner para a coleção de links
	
	@ApiModelProperty(value="ID da cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(value="Nome da cidade", example = "Uberlândia")
	private String nome;
	
	
	private EstadoModel estado;
	
	// Alteração para vir apenas o nome do Estado (usado no endpoint do restaurante)
	//Criada a classe CidadeResumoModel
	
}
