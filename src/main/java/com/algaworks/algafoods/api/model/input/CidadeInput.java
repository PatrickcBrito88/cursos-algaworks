package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
	
	@ApiModelProperty(example = "Uberlândia", required=true)
	@NotNull
	private String nome;
	
	
	@NotNull
	private EstadoIdInput estado;
	

}
