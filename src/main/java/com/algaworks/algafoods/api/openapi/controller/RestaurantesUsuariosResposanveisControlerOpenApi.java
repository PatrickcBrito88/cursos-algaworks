package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.UsuarioModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestaurantesUsuariosResposanveisControlerOpenApi {

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Id do Restaurante não encontrado", response = Problem.class)
	})
	
	@ApiOperation("Lista Responsáveis pelo Restaurante")
	CollectionModel<UsuarioModel> listaResponsaveis(@ApiParam(value="Id de um restaurante",example="1",
		required=true) Long restauranteId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Associação executada com sucesso", response = Problem.class),
		@ApiResponse(code = 404, message = "Id de restaurante ou do usuário não encontrada", response = Problem.class)
	})
	
	@ApiOperation("Associa um Restaurante a um Responsável")
	ResponseEntity<Void> associar (@ApiParam(value="Id de um restaurante",example="1",
		required=true)Long restauranteId, @ApiParam(value="Id de um usuário",example="1", required=true) Long usuarioId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Desassociação executada com sucesso", response = Problem.class),
		@ApiResponse(code = 404, message = "Id de restaurante ou do usuário não encontrada", response = Problem.class)
	})
	
	@ApiOperation("Desassocia um Restaurante de um Responsável")
	ResponseEntity<Void> desassociar (@ApiParam(value="Id de um restaurante",
		example="1", required=true)Long restauranteId, @ApiParam(value="Id de um usuário",example="1", required=true) Long usuarioId);
}
