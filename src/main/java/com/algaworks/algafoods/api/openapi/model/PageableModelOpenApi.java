
package com.algaworks.algafoods.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {
	
	/*
	 * Este método substitui tudo que retorna no pageable. Lá no SpringFoxConfig faz a substituição do Pageable.class por este modelo de classe
	 */

	@ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
	private int page;
	
	@ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
	private List<String> sort;
	
}