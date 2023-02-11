package com.algaworks.algafoods.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {

	@ApiModelProperty(value="nome do Restaurante", example="Bela Bel", required=true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example="12.56", required=true)
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	private CozinhaIdInput cozinha;
	
	@Valid
	@NotNull
	private EnderecoInput endereco;
}
