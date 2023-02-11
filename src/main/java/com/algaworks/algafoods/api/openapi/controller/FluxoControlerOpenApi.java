package com.algaworks.algafoods.api.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafoods.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Fluxo de Pedidos")
public interface FluxoControlerOpenApi {

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "Código inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Código não encontrado", response = Problem.class)
	})
	@ApiOperation("Confirmação do pedido")
	public ResponseEntity<Void> confirmar (@ApiParam (value="Código do pedido em UUID", example= "2CA263F1-5C94-11E0-84CC-002170FBAC5B",
		required=true) String codigoPedido);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "Código inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Código não encontrado", response = Problem.class)
	})
	@ApiOperation("Cancelamento do pedido")
	public ResponseEntity<Void> cancelar (@ApiParam (value="Código do pedido em UUID", example= "2CA263F1-5C94-11E0-84CC-002170FBAC5B",
			required=true) String codigoPedido);	
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "Código inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Código não encontrado", response = Problem.class)
	})
	@ApiOperation("Entrega do pedido")
	public ResponseEntity<Void> entregar (@ApiParam (value="Código do pedido em UUID", example= "2CA263F1-5C94-11E0-84CC-002170FBAC5B",
			required=true) String codigoPedido);
	
}
