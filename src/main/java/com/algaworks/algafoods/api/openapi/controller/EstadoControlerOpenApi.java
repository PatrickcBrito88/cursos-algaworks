package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.EstadoModel;
import com.algaworks.algafoods.api.model.input.EstadoInput;
import com.algaworks.algafoods.domain.model.Estado;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Estados")
public interface EstadoControlerOpenApi {

	@ApiOperation("Listar todos os estados")
	CollectionModel<EstadoModel> listar();

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	@ApiOperation("Buscar um estado pelo ID")
	EstadoModel buscar(@ApiParam(example="1", required=true) Long estadoId);

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Estado cadastrado com sucesso", response = Problem.class)
	})
	@ApiOperation("Salvar um estado")
	EstadoModel salvar(@ApiParam(name="corpo", value="Representação de um novo Estado") EstadoInput estadoInput);

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	@ApiOperation("Atualizar um estado pelo ID")
	EstadoModel atualizar(@ApiParam(example="1",required=true)Long estadoId, 
			@ApiParam(name="corpo", value="Representação de um novo Estado") EstadoInput estadoInput);

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Estado removido com sucesso", response = Problem.class)
	})
	@ApiOperation("Remover um estado pelo ID")
	void remover(@ApiParam(example="1", required=true)Long estadoId);
	
}
