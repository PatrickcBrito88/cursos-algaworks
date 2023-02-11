package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInput {

	@ApiModelProperty(example = "Cozinha Brasileira", required=true)
	@NotNull
	private String nome;
	
}
