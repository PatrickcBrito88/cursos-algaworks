package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.ProdutoModel;
import com.algaworks.algafoods.api.model.input.ProdutoInput;
import com.algaworks.algafoods.domain.model.Produto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface ProdutoControlerOpenApi {

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Produto cadastrado em um restaurante", response = Problem.class)
	})
	@ApiOperation("Cadastra um produto de um restaurante")
	ProdutoModel salvar(@ApiParam (value="Id de um Restaurante", example="1", required=true)Long restauranteId,
			@ApiParam(name="corpo", value="Representação de um Produto") ProdutoInput produtoInput);

	
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	
	@ApiOperation("Lista produtos de um restaurante")
	CollectionModel<ProdutoModel> listar(@ApiParam (value="Id de um Restaurante", example="1", required=true)Long restauranteId,
			@ApiParam(value="Boolean para incluir inativos", example="true") Boolean incluirInativos);

	
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do produto ou do restaurante inválidos", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrados", response = Problem.class)
	})
	@ApiOperation(value="Cadastra um produto de um restaurante")
	ProdutoModel buscar(@ApiParam (value="Id de um Restaurante", example="1", required=true)Long restauranteId, 
			@ApiParam (value="Id de um Produto", example="1", required=true)Long produtoId);
	
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do produto ou do restaurante inválidos", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante ou produto não encontrados", response = Problem.class)
	})
	@ApiOperation("Atualiza um produto de um restaurante")
	ProdutoModel atualizar(Long restauranteId, Long produtoId,
			ProdutoInput produtoInput);
	
}
