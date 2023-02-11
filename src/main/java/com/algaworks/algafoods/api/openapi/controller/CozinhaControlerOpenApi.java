package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.input.CozinhaInput;
import com.algaworks.algafoods.domain.model.Cozinha;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Cozinhas")
public interface CozinhaControlerOpenApi {

	@ApiOperation("Listar Cozinhas")
	PagedModel<CozinhaModel> listar(Pageable pageable);
	
	@ApiOperation("Listar Cozinhas por ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel buscar(@ApiParam(value="Id da cozinha", example="1", required=true) Long id);
	
	
	@ApiOperation("Cadastrar Cozinhas")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Cozinha cadastrada", response = Problem.class)
	})
	CozinhaModel adicionar (@ApiParam(name="corpo", value="Inclusão de uma nova cozinha")CozinhaInput cozinhaInput);
	
	
	@ApiOperation("Atualizar Cozinhas por ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 200, message = "Cozinha atualizada", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel atualizar (@ApiParam(value="Id da cozinha", example="1", required=true)Long cozinhaId,
			@ApiParam(name="corpo", value="Inclusão de uma cozinha atualizada") CozinhaInput cozinhaInput);
	
	@ApiOperation("Remover Cozinhas por ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Cozinha excluída", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	void remover (@ApiParam(value="Id da cozinha", example="1", required=true)Long cozinhaId);
	
}
