package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {

	@ApiModelProperty(value="Senha Atual")
	@NotBlank
	private String senhaAtual;
	
	@ApiModelProperty(value="Nova Senha")
	@NotBlank
	private String senhaNova;
}
