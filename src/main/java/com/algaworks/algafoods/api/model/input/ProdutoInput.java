package com.algaworks.algafoods.api.model.input;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafoods.domain.model.Restaurante;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

	@ApiModelProperty(value="nome do produto", example="Picanha", required=true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(value="descrição do produto", example="Bem passada", required=true)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(value="preço do produto", example="25.65", required=true)
	@NotNull
	private BigDecimal preco;
	
	@ApiModelProperty(example="true", required=true)
	@NotNull
	private boolean ativo;
	
}
