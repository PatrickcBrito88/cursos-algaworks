package com.algaworks.algafoods.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "foto")
@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {
	
	@ApiModelProperty(example="aabd30c2-f535-4eca-bbde-36a4c44bade3_picanha.jpg")
	private String nomeArquivo;
	
	@ApiModelProperty(example="Imagem da picanha")
	private String descricao;
	
	@ApiModelProperty(example="image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example="11930 - em bytes")
	private Long tamanho;

}
