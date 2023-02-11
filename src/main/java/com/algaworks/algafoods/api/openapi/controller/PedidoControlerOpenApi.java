package com.algaworks.algafoods.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.PedidoModel;
import com.algaworks.algafoods.api.model.PedidoResumoModel;
import com.algaworks.algafoods.api.model.input.PedidoInput;
import com.algaworks.algafoods.domain.model.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Pedidos")
public interface PedidoControlerOpenApi {

	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", 
					name = "campos", paramType = "query", type = "string") })
	@ApiOperation("Pesquisar um pedido com filtro")
	PagedModel<PedidoResumoModel> pesquisar(Pageable pageable, PedidoFilter pedidoFilter);

	
	
	
	
	
//Explicação lá na classe do SpringFoxConfig
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", 
					name = "campos", paramType = "query", type = "string") })
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	@ApiOperation("Pesquisar um pedido pelo código")
	PedidoModel buscar(@ApiParam(value="código de busca em UUID", example="2CA263F1-5C94-11E0-84CC-002170FBAC5B", required=true)
		String codigoPedido);

	
	
	
	
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Pedido salvo", response = Problem.class)
	})@ApiOperation("Salvar um pedido")
	PedidoModel salvar(@ApiParam(name="corpo", value="Representação de um novo pedido")PedidoInput pedidoInput);

}
