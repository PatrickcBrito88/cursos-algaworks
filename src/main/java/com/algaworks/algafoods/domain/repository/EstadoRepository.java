package com.algaworks.algafoods.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoods.domain.model.Estado;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long>{
	

}
