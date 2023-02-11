package com.algaworks.algafoods.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafoods.core.validation.Groups;

import lombok.Data;

@Entity
@Data
public class Estado {
	
	@NotNull(groups = Groups.CidadeId.class)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable=false)
	private String nome;
	
	
	

}
