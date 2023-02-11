package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControlerOpenApi {

	
	@ApiOperation("Listar Permissões associadas a um grupo")
	List<PermissaoModel> listarPermissoes (@ApiParam(value="Id de um grupo", example="1", required=true) Long grupoId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrados", response = Problem.class),
		@ApiResponse(code = 204, message = "Associação feita com sucesso", response = Problem.class)
	})
	@ApiOperation("Associar permissões a um grupo")
	void adicionarPermissao (@ApiParam(value="Id de um grupo", example="1", required=true)Long grupoId,
			@ApiParam(value="Id de uma permissão", example="1", required=true) Long permissaoId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrados", response = Problem.class),
		@ApiResponse(code = 204, message = "Desassociação feita com sucesso", response = Problem.class)
	})
	@ApiOperation("Retirar associação de permissões a um grupo")
	void removerPermissao (@ApiParam(value="Id de um grupo", example="1", required=true)Long grupoId,
			@ApiParam(value="Id de uma permissão", example="1", required=true)Long permissaoId);
	
}
