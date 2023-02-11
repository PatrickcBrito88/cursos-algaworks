package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {
	
	@ApiModelProperty(value="Nome do Grupo", example = "Gerente", required = true)
	@NotBlank
	private String nome;
	
}
