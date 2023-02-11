package com.algaworks.algafoods.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.*;

import com.algaworks.algafoods.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;


//@JsonRootName("gastronomia") - Muda o nome da representação da entidade no XML
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {
	
	//Tirou o groups pq vai usar outra classe para fazer input
	//@NotNull(groups = Groups.CozinhaId.class)// Essa constraint está agrupada no grupo  Cadastro Restaurante
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore - Remove da representação
	//@JsonProperty("titulo")// Define o nome na representação JSON
	@Column(nullable=false)
	@NotBlank
	private String nome;
	
	@JsonIgnore//Quando for serializar, ignora esta propriedade, senão fica em loop infinito
	@OneToMany(mappedBy="cozinha")// FOi mapeado por cozinha na entidade restaurante
	private List<Restaurante> restaurantes = new ArrayList<>();

	
			
}
