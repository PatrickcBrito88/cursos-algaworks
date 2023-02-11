package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafoods.api.model.RestauranteBasicoModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.api.model.input.RestauranteInput;
import com.algaworks.algafoods.api.openapi.model.RestauranteBasicoOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControlerOpenApi {

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Restaurante Cadastrado", response = Problem.class)})
	@ApiOperation("Cadastro de um Restaurante")
	RestauranteModel Cadastro(RestauranteInput restauranteInput);

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 200, message = "Restaurante atualizado", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	@ApiOperation("Atualização de um Restaurante por um ID")
	RestauranteModel Atualizar(@ApiParam(example="1", required=true)Long restauranteId,
			RestauranteInput restauranteInput);
	/*
	 * Para o funcionamento são 2 operações. Para a documentação é uma operação só com um Parâmetro apenas-nome
	 */
	
	@ApiImplicitParams({@ApiImplicitParam (value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",//para colocar o filtro
			name = "projecao", paramType = "query", type = "string", required=false)//required=false - Não é obrigatório
	})
	@ApiOperation(value="Listar todos os restaurantes", response = RestauranteBasicoOpenApi.class) //Significa, na documentação, não usa o RestauranteModel mas sim o RestauranteBasicoOpenApi
	CollectionModel<RestauranteBasicoModel> listar();
	
	
	@ApiOperation(value = "Lista Restaurantes", hidden=true)//Hidden=true, significa que essa operação fica oculta pois vai aparecer a de cima
	CollectionModel<RestauranteApenasNomeModel> listarApenasNome();
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	@ApiOperation("Busca de um Restaurante por um ID")
	RestauranteModel buscar(@ApiParam(example="1", required=true)Long id);

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante ativado com sucesso", response = Problem.class)
	})
	@ApiOperation("Ativação de um restaurante por um ID")
	ResponseEntity<Void> ativar(@ApiParam(example="1", required=true)Long restauranteId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante inativado com sucesso", response = Problem.class)
	})
	@ApiOperation("Inativação de um Restaurante por um ID")
	ResponseEntity<Void> inativar(@ApiParam(example="1", required=true)Long restauranteId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante aberto com sucesso", response = Problem.class)
	})
	@ApiOperation("Abertura de um Restaurante por um ID")
	ResponseEntity<Void> abrir(@ApiParam(example="1", required=true)Long restauranteId);

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante fechado com sucesso", response = Problem.class)
	})
	@ApiOperation("Fechamento de um Restaurante por um ID")
	ResponseEntity<Void> fechar(@ApiParam(example="1", required=true)Long restauranteId);

	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso", response = Problem.class)
	})	
	@ApiOperation("Ativação em massa de Restaurantes")
	void ativacoes(@ApiParam(example="[1,2,3]", required=true)List<Long> restauranteIds);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Restaurantes desativados com sucesso", response = Problem.class)
	})	
	@ApiOperation("Desativação em massa de Restaurantes")
	void desativacoes(@ApiParam(example="[1,2,3]", required=true)List<Long> restauranteIds);
}
