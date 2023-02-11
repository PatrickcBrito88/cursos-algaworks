package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface GruposUsuariosControlerOpenApi {

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	@ApiOperation("Lista os grupos")
	List<GrupoModel> listarGrupos (@ApiParam(example="1")Long usuarioId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Associação feita com sucesso", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	@ApiOperation("Associa um usuário a um grupo")
	void associar (@ApiParam(example="1")Long usuarioId, @ApiParam(example="1")Long grupoId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Desassociação feita com sucesso", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	@ApiOperation("Desassocia um usuário de um grupo")
	void desassociar (@ApiParam(example="1")Long usuarioId, @ApiParam(example="1")Long grupoId);
	
}
