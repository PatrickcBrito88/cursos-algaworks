package com.algaworks.algafoods.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // Não deve ser levada em conta como Repository
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {
	
	/*
	 * o <T, ID> é um generic, ou seja poderá ser usada por qualquer entidade
	 * Se eu tipar para restaurante, apenas poderei usar para restaurante
	 */
	
	Optional<T> buscarPrimeiro();
	
	void detach (T entity);

}
