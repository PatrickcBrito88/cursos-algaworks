package com.algaworks.algafoods.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteBasicoModel extends RepresentationModel<RestauranteBasicoModel>{

	@ApiModelProperty(value="Id do Restaurante", example = "1")
	private Long id;
	
	@ApiModelProperty(value="Nome do Restaurante", example = "Bela Bel")
	private String nome;
	
	@ApiModelProperty(example="12.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
	
}
