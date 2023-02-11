package com.algaworks.algafoods.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.algaworks.algafoods.domain.model.Endereco;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.model.ItemPedido;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.model.StatusPedido;
import com.algaworks.algafoods.domain.model.Usuario;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

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
	
	@ApiModelProperty(value="Data de Confirmação do pedido", example = "2022-12-25T00:00:00Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(value="Data de Entrega do pedido", example = "2022-12-25T00:00:00Z")
	private OffsetDateTime dataEntrega;
	
	@ApiModelProperty(value="Data de Cancelamento do pedido", example = "2022-12-25T00:00:00Z")
	private OffsetDateTime dataCancelamento;
	
	private RestauranteApenasNomeModel restaurante;
	
	private UsuarioModel cliente;
	
	private FormaPagamentoModel formaPagamento;
	
	private EnderecoModel endereco;
	
	@ApiModelProperty(value="Lista de Itens", example = "2022-12-25T00:00:00Z")
	private List<ItemPedidoModel> itens = new ArrayList<>();
	
	
	
	
}
