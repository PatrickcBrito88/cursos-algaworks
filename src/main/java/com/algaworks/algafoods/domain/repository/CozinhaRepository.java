package com.algaworks.algafoods.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoods.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
	//Não se cria um repository por tabela mas sim por agregado
	
	/*
	 * CozinhaRepositoryImpl foi deletado e daqu da interface vários métodos
	 * também serão deletados pq o Spring Data JPA já fornece tudo pronto
	 */
	
	//List<Cozinha> listar(); Spring Data Jpa já tem pronto
	List<Cozinha> findTodasByNome(String nome);
	List<Cozinha> findTodasByNomeContaining(String nome);
	Optional<Cozinha> findByNome(String nome);
	boolean existsByNome (String nome);
	
	//Cozinha buscar (Long id); Spring Data Jpa já tem pronto
	//Cozinha salvar (Cozinha cozinha); Spring Data Jpa já tem pronto
	//void remover (Long id); Spring Data Jpa já tem pronto
	
	
}
