package com.algaworks.algafoods.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafoods.domain.model.Restaurante;

public interface RestauranteRepositoryQuery {

	List<Restaurante> find(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}