package com.algaworks.algafoods.infrastrucutre.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.StatusPedido;
import com.algaworks.algafoods.domain.model.dto.VendaDiaria;
import com.algaworks.algafoods.domain.model.filter.VendaDiariaFilter;
import com.algaworks.algafoods.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{

	//Esta classe de implementação implementa o repositório criado	
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, 
			String timeOffSet) {
		//Não criou assembler pois é uma classe simples. Apenas de leitura
		//Primeiro faz o SQL no workbenck para testar	
		
		/*
		 * Tudo abaixo corresponde a essa pesquisa aqui
		 * select date(p.data_criacao) as data_criacao,
			count(p.id) as totalVendas,
			sum(p.valor_total) as total_faturado

			from pedido p 

			group by date(data_criacao)
		 */
		
		//Aula 13.14
		
		var builder = manager.getCriteriaBuilder();// Cria um builder de criteria
		var query = builder.createQuery(VendaDiaria.class);// O que eu espero de retorno ? Espero um Venda Diária.class
		var root = query.from(Pedido.class);//De onde é o from da pesquisa ?
		
		//Array de predicates
		var predicates = new ArrayList<Predicate>();
		
		/*
		 * Esses var function são funções que usamos no SQL por exemplo:
		 * select date(p.data_criacao) as data_criacao
		 * Ou então
		 * select date(convert_tz(p.data_criacao, '+00:00', '-03:00')) as data_criacao
		 */
		
		var functionConvertTzDataCriacao = builder.function("convert_tz", 
				Date.class, root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffSet));
		/* 
		 * Essa fução de cima faz o seguinte:
		 * select date(convert_tz(p.data_criacao, '+00:00', '-03:00')) as data_criacao
		 */
		
				
		var functionDateDataCriacao = builder.function(
				"date", Date.class, functionConvertTzDataCriacao);
				
		var selection = builder.construct(VendaDiaria.class,
				functionDateDataCriacao, 
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		//A ordem de retorno respeita a ordem que a classe foi construída (VendaDiaria)
		
		//Filtro do id
		if (filtro.getRestauranteId()!=null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("id"), 
					filtro.getRestauranteId()));
		}
		
		//Filtro do Data Criação Início
		if (filtro.getDataCriacaoInicio()!=null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoInicio()));
		}
		
		//Filtro do data criação fim
		if (filtro.getDataCriacaoFim()!=null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
					filtro.getDataCriacaoFim()));
		}
		
		//Filtro status
		predicates.add(root.get("status").
				in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection);//Faz a seleção
		query.groupBy(functionDateDataCriacao); //Faz o group by
		query.where(predicates.toArray(new Predicate[0]));//Acrescenta o filtro (Where)
		
		
		return manager.createQuery(query).getResultList();
		
	}

}
