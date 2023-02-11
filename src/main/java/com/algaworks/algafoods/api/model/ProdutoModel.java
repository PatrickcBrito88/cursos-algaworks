package com.algaworks.algafoods.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel>{

	@ApiModelProperty(value="Id do produto", example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome do produto", example="Picanha")
	private String nome;
	
	@ApiModelProperty(value="Descrição do produto", example="Bem passada")
	private String descricao;
	
	@ApiModelProperty(value="Preço do produto", example="25.65")
	private BigDecimal preco;
	
	@ApiModelProperty(value="Indica se o produto está ativo ou não", example="true")
	private boolean ativo;	
	
}
