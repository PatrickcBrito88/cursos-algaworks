package com.algaworks.algafoods.api.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RestauranteBasicoModel")
public class RestauranteBasicoOpenApi {
	
	/*
	 * Serve apenas para documentação. Bom para usar quando tem JsonView
	 * 
	 */
	
	

	@ApiModelProperty(example="1")
	private Long id;
	
	@ApiModelProperty(example="Bela Bel")
	private String nome;
	
	@ApiModelProperty(example="12.00")
	private BigDecimal precoFrete;
	
	private CozinhaModel cozinha; 
}
