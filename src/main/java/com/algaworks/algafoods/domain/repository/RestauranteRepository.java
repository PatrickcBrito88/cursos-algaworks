package com.algaworks.algafoods.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoods.domain.model.Produto;
import com.algaworks.algafoods.domain.model.Restaurante;

@Repository
public interface RestauranteRepository 
extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQuery,
JpaSpecificationExecutor<Restaurante>{

	List<Restaurante> findByTaxaFreteBetween (BigDecimal taxaInicial, BigDecimal taxaFinal);
	//Também podemos usar o prefixo Read, query, get, stream ao invés do findBy
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param ("id") Long cozinhaId);
	
	//List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId); Mesmo método de cima
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	//@Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
	/*
	 * QUando se faz um join em cozinha o fetch é automático
	 * Quando se faz em um manytomany tem que usar a palavra chave fetch
	 * Left Fetch significa que mesmo que ocorra linhas null, o resultado será mostrado
	 * Fetch significa Busca. Vamos usar o fetch até onde não precisa
	 */
	List<Restaurante> findAll();
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinha);
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, 
			BigDecimal taxaFreteFinal);
	
}
