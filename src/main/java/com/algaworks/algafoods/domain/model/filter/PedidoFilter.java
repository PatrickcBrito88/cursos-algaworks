package com.algaworks.algafoods.domain.model.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {

	// Classe que representa a pesquisa que queremos fazer
	
	//A pesquisa está dentro de spec
	@ApiModelProperty(value="Id de um cliente", example="1")
	private Long clienteId;
	
	@ApiModelProperty(value="Id de um restaurante", example="1")
	private Long restauranteId;
	
	@ApiModelProperty(value="Data/hora de criação inicial para filtro da pesquisa", example="2019-10-30T00:00:00Z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	
	@ApiModelProperty(value="Data/hora de criação inicial para filtro da pesquisa", example="2019-10-30T00:00:00Z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
}
