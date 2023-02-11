package com.algaworks.algafoods.infrastrucutre.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.filter.PedidoFilter;


public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
		return (root, query, builder) -> {
			if (Pedido.class.equals(query.getResultType())) {// Incluído para poder realizar o count da paginação junto com o fetch
			root.fetch("restaurante").fetch("cozinha"); //Para reduzir o problema de n+1 e diminuir o número de consultas no banco
			/* 
			 * No fetch colocamos tudo que aquela tabela tem. 
			 * Tabela pedidos tem restaurante e tem cliente mas não tem cozinha
			 * Se eu colocar um root.fetch("cozinha") vai dar erro pq cozinha não está em Pedido
			 * Cozinha está em restaurante e restaurante está em pedido
			 * Então fica root.fetch("restaurante").fetch("cozinha")
			 */
			
			root.fetch("cliente"); //Para reduzir o problema de n+1 e diminuir o número de consultas no banco
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId())); 
				//Busca na entidade Pedido, o atributo cliente que possui o cliente que está sendo passado no parâmetro
			}

			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId())); 
				//Busca na entidade Pedido, o atributo restaurante que possui o restaurante que está sendo passado no parâmetro
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoInicio())); 
				//Busca na entidade Pedido, o atributo datacriacação que possui o dataCriação que está sendo passado no parâmetro
			}
			
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoFim())); 
				//Busca na entidade Pedido, o atributo datCriação que possui o dataCriação que está sendo passado no parâmetro
			}
			
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		
	}
}
