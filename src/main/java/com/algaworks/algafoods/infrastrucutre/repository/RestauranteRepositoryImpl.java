package com.algaworks.algafoods.infrastrucutre.repository;

import static com.algaworks.algafoods.infrastrucutre.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafoods.infrastrucutre.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.repository.RestauranteRepository;
import com.algaworks.algafoods.domain.repository.RestauranteRepositoryQuery;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	/* 
	 * Sem o Lazy o RestauranteRepository ficará em loop infinito.
	 * QUando o RestauranteRepositoryImpl é instanciado pelo Eclipse, ele chama o RestauranteRepository
	 * e fica nesse LoopInfinito
	 * O lazy significa, instancie apenas quando de fato for preciso
	 */
	
	
	@Override
	public List<Restaurante> find (String nome, BigDecimal taxaFreteInicial, 
			BigDecimal taxaFreteFinal){
		
		//Primeira Forma
		//var jpql = "from Restaurante where nome like :nome "
			//	+ "and taxaFrete between :taxaInicial and :taxaFinal";
		
		//Segunda Forma (Dinâmica)
		//var jpql2 = new StringBuilder();
		//jpql2.append("from Restaurante where 0 = 0 ");
		
		//var parametros = new HashMap<>();
		
		//if (StringUtils.hasLength(nome)) {
			//jpql2.append("and nome like :nome ");
			//parametros.put("nome", "%"+nome+"%");
		//}
		
		//if (taxaFreteInicial!= null) {
			//jpql2.append("and taxaFrete >= :taxaInicial ");
			//parametros.put("taxaInicial", taxaFreteInicial);
		//}
		
		//if (taxaFreteFinal!= null) {
			//jpql2.append("and taxaFrete <= :taxaFinal");
			//parametros.put("taxaFinal", taxaFreteFinal);
		//}
		
		//TypedQuery<Restaurante> query = manager.createQuery(jpql2.toString(), Restaurante.class);
		
		//Para cada chave/valor (existente) atribua o parametro a query
		//parametros.forEach((chave, valor) -> query.setParameter((String) chave, valor));
		
		//return query.getResultList();
		
		CriteriaBuilder builder  = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);//cria um construtor
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasText(nome)) {
		predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));//  cria critérios de pesquisa
		}
		
		if (taxaFreteInicial!= null) {
		predicates.add(builder.greaterThan(root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if (taxaFreteFinal != null) {
		predicates.add(builder.lessThan(root.get("taxaFrete"), taxaFreteFinal));
		}
		
				
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		
		return query.getResultList();
				
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis()
				.and(comNomeSemelhante(nome)));
	}

}
