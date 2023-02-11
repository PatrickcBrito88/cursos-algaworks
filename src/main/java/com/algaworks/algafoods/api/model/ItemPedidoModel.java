package com.algaworks.algafoods.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel>{

	@ApiModelProperty(value="Id do Produto", example = "1")
	private Long produtoId;
	
	@ApiModelProperty(value="Nome do produto", example = "Pernil Suíno")
	private String produtoNome;
	
	@ApiModelProperty(value="Quantidade do item no pedido", example = "50")
	private Integer quantidade;
	
	@ApiModelProperty(value="Preço unitário do pedido", example = "10.35")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(value="Preço total do pedido", example = "100.56")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(value="Observação do item pedido", example = "Menos picante por favor")
	private String observacao;
}
