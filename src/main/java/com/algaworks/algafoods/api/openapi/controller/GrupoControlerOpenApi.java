package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.GrupoModel;
import com.algaworks.algafoods.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Grupos")
public interface GrupoControlerOpenApi {

	@ApiOperation("Listar grupos")
	List<GrupoModel> listar();
	
	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoModel buscar(@PathVariable Long grupoId);
	
	@ApiOperation("Adiciona um grupo ")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Grupo Cadastrado", response = Problem.class)
	})
	GrupoModel adicionar(@ApiParam(name = "corpo", 
		value="Representação de um novo grupo")GrupoInput grupoInput);
		
	@ApiOperation("Atualiza um grupo por um ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 200, message = "Grupo atualizado", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoModel atualizar(@ApiParam(value="Id de um grupo", example="1", required=true)Long grupoId,
			@ApiParam(name = "corpo", value="Representação de um novo grupo")GrupoInput grupoInput);
		
	@ApiOperation("Remove um grupo por um ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Grupo deletado", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	void remover(@ApiParam(example="1", required=true)Long grupoId);
	
	
}
