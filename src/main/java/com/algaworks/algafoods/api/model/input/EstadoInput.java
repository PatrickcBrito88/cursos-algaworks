package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInput {
	
	@ApiModelProperty(example="Rio de Janeiro", required=true)
	@NotNull
	private String nome;

}
