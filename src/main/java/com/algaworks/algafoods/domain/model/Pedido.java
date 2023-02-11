package com.algaworks.algafoods.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafoods.domain.events.PedidoCanceladoEvent;
import com.algaworks.algafoods.domain.events.PedidoConfirmadoEvent;
import com.algaworks.algafoods.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Pedido extends AbstractAggregateRoot<Pedido>{//Extender abstractAggregatteRoot para ouvir os eventos
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private String codigo;
	
	@Column(name="subtotal", nullable=false)
	private BigDecimal subtotal;
	
	@Column(name="taxa_frete", nullable=false)
	private BigDecimal taxaFrete;
	
	@Column(name="valor_total", nullable=false)
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(name="data_criacao", nullable=false)
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	
	@Embedded
	private Endereco endereco;
	
	@Enumerated(EnumType.STRING)// Se não colocar o EnumType. String ele entende apenas número vindo do SQL
	private StatusPedido status=StatusPedido.CRIADO;
	
	@OneToMany (mappedBy = "pedido", cascade = CascadeType.ALL)//Ou seja, ao salvar o pedido, também quero salvar os itens do pedido
	private List<ItemPedido> itens = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="usuario_cliente_id", nullable=false)
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(name="restaurante_id", nullable=false)
	private Restaurante restaurante;
	
	@ManyToOne(fetch = FetchType.LAZY)//Só vai fazer trazer a consuta quando de fato for necessário
	@JoinColumn(name="forma_pagamento_id", nullable=false)
	private FormaPagamento formaPagamento;
	
	//Calcula o valor total do Pedido
	public void calcularValorTotal() {
		getItens().forEach(ItemPedido::calculaPrecoTotal);
		
	    this.subtotal = getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    this.valorTotal = this.subtotal.add(this.taxaFrete);
	}
	
	//Aula 12.24
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		
		registerEvent(new PedidoConfirmadoEvent(this));//Instancia a classe que representa o evento. This significa que está mandando este pedido como parametro
	}
	
	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		
		registerEvent(new PedidoCanceladoEvent(this));
	}
	
	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}
	
	private void setStatus(StatusPedido novoStatus) {
		if (getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(
					String.format("Status do pedido de código %s não pode ser alterado de %s para %s",
							getCodigo(),getStatus().getDescricao(), novoStatus.getDescricao()));
		}
		this.status=novoStatus;
	}
	
	@PrePersist //Método de callback. Neste caso está sendo executado na pré persistência
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}
	
	public boolean podeSerConfirmado() {
		return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
	}
	
	public boolean podeSerCancelado() {
		return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
	}
	
	public boolean podeSerEntregue() {
		return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
	}

	
}
