package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@ApiModelProperty(value="Id do produto", example="1", required=true)
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(value="Quantidade do item no pedido", example="10", required=true)
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	@ApiModelProperty(value="Observação do item", example="Muito picante por favor", required=true)
	private String observacao;
	
}
