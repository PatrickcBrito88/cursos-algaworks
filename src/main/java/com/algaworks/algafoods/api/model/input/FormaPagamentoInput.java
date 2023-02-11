package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInput {

	@ApiModelProperty(value="Descrição da Forma de Pagamento", example = "Cartão de Débito", required = true)
	@NotBlank
	private String descricao;
	
}
