package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.algaworks.algafoods.domain.model.dto.VendaDiaria;
import com.algaworks.algafoods.domain.model.filter.VendaDiariaFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estatísticas")
public interface EstatisticaControlerOpenApi {

	@ApiOperation("Consulta de vendas diárias")
	@ApiImplicitParams({
			@ApiImplicitParam(name="restauranteId",	
			value="Id do Restaurante", example="1", dataType="int"),
			
			@ApiImplicitParam(name="dataCriacaoInicio",
			value="Data de Criação (Início)", example="2022-12-31T00:00:00Z", dataType="data-time"),
			
			@ApiImplicitParam(name="dataCriaçãoFim", 
			value="Data de Criação (Fim)",example="2022-12-31T00:00:00Z", dataType="data-time")})
	List<VendaDiaria> consultarVendasDiarias(
			
			VendaDiariaFilter filtro,
			
			@ApiParam(value="Deslocamento de horário a ser considerado na "
					+ "consulta em relação ao UTC", defaultValue = "+00:00")
			String timeOffSet);
	
	/*
	 * Neste caso foi usado ImplicitParam pq o VendaDiariaFilter fica no pacote de domínio e 
	 * não é uma boa prática, colocar implementações do Swagger (que é de API) dentro de classe
	 * de domínio.
	 */
	
	
	@ApiOperation("Consulta de vendas com relatório")
	ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
			String timeOffSet);
}
