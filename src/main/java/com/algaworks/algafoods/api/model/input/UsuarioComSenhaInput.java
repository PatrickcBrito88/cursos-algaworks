package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaInput extends UsuarioInput{

	@ApiModelProperty(value="Senha de um novo usuário", example="ABC12345", required=true)
	@NotBlank
	private String senha;
	
}
