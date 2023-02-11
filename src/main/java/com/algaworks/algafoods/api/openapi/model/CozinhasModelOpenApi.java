package com.algaworks.algafoods.api.openapi.model;

import com.algaworks.algafoods.api.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaModel> {

	/* 
	 * Classe para representar a documentação das cozinhas
	 * Esta classe irá substituir a classe de cozinhaModel
	 * 
	 * Lá no SpringFoxConfig chama um método que substitui apenas um método. No caso Page<CozinhasModel> por esta classe
	 * 
	 * Tudo para ficar condinzente com o que aparece na realidade
	 */
	
	//Toda a implementação desta classe está na PageModelOpenApi, pois aí serve para todas as classes que a herdarem
	
}
