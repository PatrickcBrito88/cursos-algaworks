package com.algaworks.algafoods.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafoods.core.validation.Groups;
import com.algaworks.algafoods.core.validation.Multiplo;
import com.algaworks.algafoods.core.validation.TaxaFrete;
import com.algaworks.algafoods.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField="taxaFrete", descricaoField="nome", 
descricaoObrigatoria="Frete Grátis")
/*ValorField - Qual o nome do campo desta classe restaurante que eu quero checar ?
 * DescricaoField - Qual o nome desta classe atual que eu vou verificar ?
 * Ou seja, verifica se taxaFrete é zero, se for verifica se o nome possui a descrição
 * FreteGratis, se possuir, ok, se não possuir irá falhar
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull //Aceita String Vazio
//	@NotEmpty//Não aceita String vazia "" mas aceita com espaço "    "
//	@NotBlank //(groups = Groups.CadastroRestaurante.class)// Essa constraint está agrupada no grupo  Cadastro Restaurante
	@Column(nullable=false)
	private String nome;
	
//	@DecimalMin("0") é a mesma coisa que @PositiveorZero
//	@PositiveOrZero//(message="{TaxaFrete.invalida}")
	//(groups = Groups.CadastroRestaurante.class)// Essa constraint está agrupada no grupo  Cadastro Restaurante
//	@NotNull
	//@TaxaFrete
	//@Multiplo(numero=5)
	@Column (name="taxa_frete", nullable=true)
	private BigDecimal taxaFrete;

	
	//@JsonIgnoreProperties({"hibernateLazyInitializer"})//Ignora a propriedade do proxy criado em tempo de execução
	@JsonIgnoreProperties(value="nome", allowGetters = true)
	//Quando colocamos o value no atributo, abre a opção de allogetter e assim pertmitir 
	@ManyToOne// (fetch = FetchType.LAZY)//Muitos restauraute possuem 1 cozinha - Tudo que termina com toOne é Eager (Ansioso)
	@JoinColumn(name = "cozinha_id", nullable=true) //- Renomeia uma coluna que venha com o ManytoOne
//	@NotNull //(groups = Groups.CadastroRestaurante.class)// Essa constraint está agrupada no grupo  Cadastro Restaurante
//	@Valid//Desta forma irá validar em cascata - A partir de agora eu quero q vc valide as propriedades de cozinha e não apenas a cozinha
//	@ConvertGroup(from=Default.class, to=Groups.CozinhaId.class) // Tem que importar o javax.validation.groups.default se nãoo, não funciona
	/*Colocou Valid no controller do Restaurante. Esta dizendo, na hora de validar a cozinha,	converta o que é grupo default para group CadastroRestaurante*/
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded //Estou buscando algo embarcado
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.TRUE;
	
	//@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false)
	private OffsetDateTime dataCadastro;
	
	//@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false)// datetime tira a precisão de milisegundos
	private OffsetDateTime dataAtualizacao;
	
	@JsonIgnore//Foi ignorado para aliviar o payload
	@ManyToMany //	(fetch = FetchType.EAGER)//Tudo que termina com toMany é Lazy(Preguiçoso, feito conforme a demanda)
	@JoinTable(name = "restaurante_forma_pagamento",
	joinColumns = @JoinColumn (name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn (name = "forma_pagamento_id"))
	private Set<FormaPagamento> formaPagamento=new HashSet<>();
	//Foi modificado para Set e em Assembler para Collection pois assim é possível incluir a mesma chave, 
	//sem precisar ficar dando erro de constraint já que a chave já está inclusa
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
	joinColumns = @JoinColumn(name="restaurante_id"),
	inverseJoinColumns = @JoinColumn (name = "usuario_id"))
	private Set<Usuario> usuariosResponsaveis=new HashSet<>();
	
	
	@JsonIgnore
	@OneToMany (mappedBy="restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
//	@OneToMany(mappedBy = "restaurante")
//	private List<Pedido> pedidos = new ArrayList<>();

	public boolean podeAbrir() {
		if ((this.aberto==false)&&(this.ativo==true)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean podeFechar() {
		return !podeAbrir();
	}
	
	public boolean podeAtivar() {
		if (this.ativo==false) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean podeInativar() {
		return !podeAtivar();
	}
	
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public void abrir() {
		setAberto(true);
	}
	
	public void fechar() {
		setAberto(false);
	}
	
	public boolean removeFormaPagamento (FormaPagamento formaPagamentoInput) {
		return formaPagamento.remove(formaPagamentoInput);
	}
	
	public boolean adicionaFormaPagamento (FormaPagamento formaPagamentoInput) {
		return formaPagamento.add(formaPagamentoInput);
	}
	
	public boolean adicionaResponsavel (Usuario usuario) {
		return getUsuariosResponsaveis().add(usuario);
	}
	
	public boolean removeResponsavel (Usuario usuario) {
		return getUsuariosResponsaveis().remove(usuario);
	}
	
	public boolean aceitaFormaPagamento (FormaPagamento formaPagamento) {
		return this.getFormaPagamento().contains(formaPagamento);
	}
	
	public boolean naoAceitaFormaPagamento (FormaPagamento formaPagamento) {
		return !aceitaFormaPagamento(formaPagamento);
	}
	
	
}
