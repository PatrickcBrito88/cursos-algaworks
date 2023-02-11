package com.algaworks.algafoods.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.dto.VendaDiaria;
import com.algaworks.algafoods.domain.model.filter.VendaDiariaFilter;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>,
	JpaSpecificationExecutor<Pedido>{
	//JPASpecificationExecutor -> Para usar o SPEC e o PedidoFilter
	
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido>findAll();
	
	Optional<Pedido> findByCodigo(String codigo);
	
	/*A pesquisa acima reduz o número de consultas ao banco de dados. Com apenas 1 consulta ele consegue trazer
	 * todos os resultados necessários
	 */
}
