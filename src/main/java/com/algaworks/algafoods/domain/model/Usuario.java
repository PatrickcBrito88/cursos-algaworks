package com.algaworks.algafoods.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	private String email;
	
	@Column(nullable=false)
	private String senha;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime dataCadastro;
	
	@ManyToMany
	@JoinTable(name="usuario_grupo",
	joinColumns = @JoinColumn(name="usuario_id"),
	inverseJoinColumns = @JoinColumn(name="grupo_id"))
	private Set<Grupo> grupos = new HashSet<>();
	
	
	
	public boolean senhaCoincideCom(String senha) {
		return getSenha().equals(senha);
	}
	
	public boolean senhaNaoCoincindeCom(String senha) {
		return !senhaCoincideCom(senha);
	}
	
	public boolean associar (Grupo grupo) {
		return getGrupos().add(grupo);
	}
	
	public boolean desassociar (Grupo grupo) {
		return getGrupos().remove(grupo);
	}
	
	
}
