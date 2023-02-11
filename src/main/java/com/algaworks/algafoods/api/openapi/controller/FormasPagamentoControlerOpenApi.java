package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.FormaPagamentoModel;
import com.algaworks.algafoods.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Formas de Pagamento")
public interface FormasPagamentoControlerOpenApi {


	@ApiOperation("Listar Formas de Pagamento")
	ResponseEntity<CollectionModel<FormaPagamentoModel>> listar();
	
	@ApiOperation("Buscar Formas de Pagamento por Id")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
	})
	
	ResponseEntity<FormaPagamentoModel> buscarComDeepTag(@ApiParam(value="Id da Forma de Pagamento", example="1", required=true)Long formaPagamentoId,
			ServletWebRequest request);

	@ApiOperation("Salvar Formas de Pagamento")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Forma de Pagamento cadastrada", response = Problem.class)
	})
	
	FormaPagamentoModel adicionar(@ApiParam(name="corpo", value="Inclusão de nova Forma de Pagamento")
		FormaPagamentoInput formaPagamentoInput);

	@ApiOperation("Atualizar Formas de Pagamento por ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 200, message = "Forma de Pagamento atualizada", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
	})
	
	FormaPagamentoModel atualizar(@ApiParam(value="Id da Forma de Pagamento", example="1", required=true) Long formaPagamentoId,
			@ApiParam(name="corpo", value="Inclusão de uma Forma de Pagamento atualizada") FormaPagamentoInput formaPagamentoInput);

	@ApiOperation("Remover Formas de Pagamento por ID")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Forma de Pagamento excluída", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
	})
	
	void remover(@ApiParam(value="Id da Forma de Pagamento", example="1", required=true)Long formaPagamentoId);
	
}
