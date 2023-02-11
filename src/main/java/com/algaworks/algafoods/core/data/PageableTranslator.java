package com.algaworks.algafoods.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {
	
	public static Pageable translate (Pageable pageable, Map<String, String> fieldsMapping) {
		
		var orders = pageable.getSort().stream()
		.filter(order -> fieldsMapping.containsKey(order.getProperty()))//Aqui faz um filtro. ou seja. só avança com as propriedades que contem dentro do fieldsMapping	
		.map(order -> new Sort.Order(order.getDirection(),
				fieldsMapping.get(order.getProperty())))
		.collect(Collectors.toList());
		/*
		 * Este método faz com que a propriedade passada no sort seja buscada dentro dentro do fieldsMapping (que 
		 * possui chave/Valor). Ou seja, o COnsumidor passou clienteNome, ele procura na lista de chave/valor 
		 * e faz a conversão
		 */
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(orders));
	}

}
