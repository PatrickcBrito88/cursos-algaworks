package com.algaworks.algafoods.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.CidadeModel;
import com.algaworks.algafoods.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Cidades")
//Interface para colocar as anotações das documentações
public interface CidadeControlerOpenApi {
	
	@ApiOperation("Listar Cidades")
	CollectionModel<CidadeModel> listar();
	
	
	@ApiOperation("Busca uma cidade por Id")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1", required=true) Long id);
		

	@ApiOperation("Salvar uma cidade")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Cidade cadastrada", response = Problem.class)
	})
	CidadeModel salvar(@ApiParam(name = "corpo", value="Representação de uma nova cidade")
		CidadeInput cidadeInput);

	@ApiOperation("Atualiza uma cidade por Id")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 200, message = "Cidade atualizada", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required=true)Long id, 
			@ApiParam(name = "corpo", value="Representação de uma cidade com os novos dados")
			CidadeInput cidadeInput);
	
	@ApiOperation("Remove uma cidade por Id")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Cidade excluída", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})	
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required=true) Long id);

}
