package com.algaworks.algafoods.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name="produto_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)//Na maioria das vezes que eu buscar a foto de um produto eu não precisarei buscar um produto. Por isso Lazy
	@MapsId//Significa que este atributo produto é mapeado pelo Id. 
	//Ou seja, quando fizer um get em foto.id, automaticamente o produto vai ter o id daquele objeto foto 
	private Produto produto;
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	public Long getRestauranteId() {
		if (getProduto()!=null) {
			return getProduto().getRestaurante().getId();
		}
		return null;
	}
	

}
