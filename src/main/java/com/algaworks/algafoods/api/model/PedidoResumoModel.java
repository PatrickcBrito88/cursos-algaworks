package com.algaworks.algafoods.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafoods.domain.model.Endereco;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.model.ItemPedido;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.model.StatusPedido;
import com.algaworks.algafoods.domain.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFilter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter") //Vai filtrar as propriedades que estão aqui a partir do filtro indicado - Verificar no controler
@Relation(collectionRelation = "Pedidos Resumidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel>{

	@ApiModelProperty(value="Código do pedido em UUID", example = "2CA263F1-5C94-11E0-84CC-002170FBAC5B")
	private String codigo;
	
	@ApiModelProperty(value="Subtotal do pedido", example = "52.24")
	private BigDecimal subtotal;
	
	@ApiModelProperty(value="Taxa de Frete", example = "10.00")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(value="Valor Total do Pedido", example = "62.24")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(value="Status do Pedido", example = "Confirmado")
	private String status;
	
	@ApiModelProperty(value="Data de Criação do pedido", example = "2022-12-25T00:00:00Z")
	private OffsetDateTime dataCriacao;
	
	
	private RestauranteApenasNomeModel restaurante;
	
	@ApiModelProperty(value="Nome do Cliente", example = "Antônio")
	private UsuarioModel cliente;
	
	
	
	
}
