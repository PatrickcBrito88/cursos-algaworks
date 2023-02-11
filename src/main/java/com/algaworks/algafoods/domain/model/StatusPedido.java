package com.algaworks.algafoods.domain.model;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.s3.model.GetBucketPolicyStatusRequest;

public enum StatusPedido {
	
	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),//status permitido
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusAnteriores;
	
	StatusPedido(String descricao, StatusPedido...statusAnteriores){
		this.descricao=descricao;
		this.statusAnteriores=Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	//Aula 12.24
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
		/*
		 * Passa o novo status por parâmetro
		 * Verifica se o this (este status) encontra-se no array que podem ser tramitados
		 * Nega a afirmação
		 */
	}
	
	public boolean podeAlterarPara(StatusPedido novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
		/*
		 * Passa o novo status por parâmetro
		 * Verifica se o this (este status) encontra-se no array que podem ser tramitados
		 * Nega a afirmação
		 */
	}

}
